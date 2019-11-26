package com.ftninformatika.util.elastic;

import com.ftninformatika.bisis.opac2.search.FiltersReq;
import com.ftninformatika.bisis.opac2.search.SelectedFilter;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.bisis.search.UniversalSearchModel;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.elasticsearch.index.query.*;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.query.SortQueryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Petar on 7/17/2017.
 */
public class ElasticUtility {

    private static List<String> NOT_TOKENIZED = Arrays.asList("DC", "UG", "675a", "675u", "UG", "675b", "IN", "BN"
    , "010a", "010z", "SN", "011a", "011z", "SP", "011e", "011c", "SY", "SZ");
    public static List<String> AUTOCOMPLETE_PREFIXES = Arrays.asList("authors", "publishers","titles", "keywords");
    public static String RAW_SUFFIX = "_raw";

    public static List<String> getIdsFromElasticIterable(Iterable<ElasticPrefixEntity> elasticResponse) {
        return StreamSupport.stream(elasticResponse.spliterator(), false)
                .map(ElasticPrefixEntity::getId)
                .collect(Collectors.toList());
    }


    public static ElasticPrefixEntity getEPEFromCtlgno(String ctlgNo, Iterable<ElasticPrefixEntity> elasticPrefixEntities) {
        final ElasticPrefixEntity[] retVal = {null};

        elasticPrefixEntities.forEach(
                ep -> {
                    if (ep.getPrefixes().get("IN") != null && ep.getPrefixes().size() > 0) {
                        for (String ct : ep.getPrefixes().get("IN")) {
                            if (ct.equals(ctlgNo)) {
                                retVal[0] = ep;
                                break;
                            }
                        }
                    }
                }
        );

        return retVal[0];
    }

    public static MatchQueryBuilder idExistsQuery(String inv) {
        MatchQueryBuilder query = QueryBuilders.matchQuery("prefixes.IN", inv);
        return query;
    }

    public static BoolQueryBuilder searchUniversalQuery(UniversalSearchModel universalSearchModel) {
        BoolQueryBuilder retVal = QueryBuilders.boolQuery();

        if (universalSearchModel.getSearchText() != null && !"".equals(universalSearchModel.getSearchText())) {
            //retVal.must(QueryBuilders.simpleQueryStringQuery(universalSearchModel.getSearchText()));
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.AU", LatCyrUtils.toLatinUnaccentedWithoutStopSigns(universalSearchModel.getSearchText())));
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.TI", LatCyrUtils.toLatinUnaccentedWithoutStopSigns(universalSearchModel.getSearchText())));
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.BN", LatCyrUtils.toLatinUnaccentedWithoutStopSigns(universalSearchModel.getSearchText()))); //isbn??
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.SP", LatCyrUtils.toLatinUnaccentedWithoutStopSigns(universalSearchModel.getSearchText()))); //issn
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.SB", LatCyrUtils.toLatinUnaccentedWithoutStopSigns(universalSearchModel.getSearchText()))); //predmetne odrednice
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.DC", LatCyrUtils.toLatinUnaccented(universalSearchModel.getSearchText()))); //udk??
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.KW", LatCyrUtils.toLatinUnaccentedWithoutStopSigns(universalSearchModel.getSearchText())));
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.PP", LatCyrUtils.toLatinUnaccentedWithoutStopSigns(universalSearchModel.getSearchText())));
            retVal.should(QueryBuilders.matchPhraseQuery("prefixes.PU", LatCyrUtils.toLatinUnaccentedWithoutStopSigns(universalSearchModel.getSearchText())));

            retVal.minimumShouldMatch(1);
        }

        if (universalSearchModel.getDepartments() != null && universalSearchModel.getDepartments().size() > 0) {
            for (String dep : universalSearchModel.getDepartments()) {
                retVal.must(QueryBuilders.matchQuery("prefixes.OD", dep));
            }
        }

        if (universalSearchModel.getBranches() != null && universalSearchModel.getBranches().size() > 0){
            for (String branch: universalSearchModel.getBranches())
                retVal.must(QueryBuilders.matchQuery("prefixes.SL", branch));
        }

        return retVal;
    }

    public static QueryBuilder buildQbForField(String text, String prefix) {
        QueryBuilder qb = null;
        if (text != null && !"".equals(text)) {
            //za netokenizirane prefikse posebno se proverava da li se pravi match ili wildcard
            if(NOT_TOKENIZED.contains(prefix)) {
                text = text.toLowerCase();
                if (isBarcodeIsbnIssn(prefix, text))
                    text = formatBarcodeIsbnIssnInput(text);
                if (!text.contains("*") && !text.contains("?")) {
                    qb = QueryBuilders.matchQuery("prefixes." + prefix, LatCyrUtils.toLatinUnaccented(text));
                }else {
                    String temp = text.replaceAll("\\*","").replaceAll("\\?","");
                    if(temp.length() > 0) { //ne dozvoljavamo upit koji sadrzi samo * i ?
                        qb = QueryBuilders.wildcardQuery("prefixes." + prefix,LatCyrUtils.toLatinUnaccented(text));
                    }
                }
            } else {
                //ako trazi pocetak teksta
                if (text.startsWith("~") && text.length() > 1) {
                    qb = QueryBuilders.matchPhrasePrefixQuery("prefixes." + prefix, text.substring(1)).analyzer("standard").maxExpansions(0);
                    ((QueryStringQueryBuilder) qb).autoGeneratePhraseQueries(true);
                    ((QueryStringQueryBuilder) qb).defaultOperator(QueryStringQueryBuilder.DEFAULT_OPERATOR.AND);
                }
                //ako trazi kraj teksta
                else if (text.endsWith("~") && text.length() > 1) {
//                qb = QueryBuilders.matchQuery("prefixes." + prefix, text.substring(0, text.length() - 1)
//                        + PrefixConverter.endPhraseFlag).analyzer("standard").maxExpansions(0);
                    qb = QueryBuilders.queryStringQuery("*" + LatCyrUtils.toLatinUnaccentedWithoutStopSigns(text));
                    ((QueryStringQueryBuilder) qb).defaultField("prefixes." + prefix);
                    ((QueryStringQueryBuilder) qb).autoGeneratePhraseQueries(true);
                    ((QueryStringQueryBuilder) qb).defaultOperator(QueryStringQueryBuilder.DEFAULT_OPERATOR.AND);
                } else {
                    qb = QueryBuilders.queryStringQuery(LatCyrUtils.toLatinUnaccentedWithoutStopSigns(text));
                    ((QueryStringQueryBuilder) qb).defaultField("prefixes." + prefix);
                    ((QueryStringQueryBuilder) qb).autoGeneratePhraseQueries(true);
                    ((QueryStringQueryBuilder) qb).defaultOperator(QueryStringQueryBuilder.DEFAULT_OPERATOR.AND);
                }
            }
        }
        return qb;
    }

    /**
     * Checks if search value is isbn or issn
     * from barcode scanner (without "-" signs)
     * in order to generate different query.
     */
    private static boolean isBarcodeIsbnIssn(String pref, String value) {
        if (pref == null || value == null)
            return false;
        if (!pref.equals("BN") && !pref.equals("SN"))
            return false;
        if (value.length() < 8)
            return false;
        if (value.contains("-"))
            return false;
        if (!value.matches("[0-9]+"))
            return false;
        return true;
    }

    /**
     * Format isbn/issn input without "-" signs
     * to be able to perform analog search
     * @return formatted value
     */
    private static String formatBarcodeIsbnIssnInput(String value) {
        StringBuffer retVal = new StringBuffer();
        for (char c: value.toCharArray()) {
            retVal.append(c);
            retVal.append("*");
        }
        retVal.delete(retVal.length() - 1, retVal.length());
        return retVal.toString();
    }

    //formiranje Query-ja za glavnu pretragu zapisa
    public static BoolQueryBuilder makeQuery(SearchModel sm) {

        //ovo je zbog vrste autorstva Pera Peric mentor, sve se trazi u jednom polju AU
        if (sm.getValueForPrefix("AU") != null && !sm.getValueForPrefix("AU").equals("") && sm.getValueForPrefix("TA") != null) {
            sm.setValueForPrefix("AU", sm.getValueForPrefix("AU") + " " + sm.getValueForPrefix("TA"));
            sm.setValueForPrefix("TA", "");
        }

        BoolQueryBuilder retVal = QueryBuilders.boolQuery();

        try {
            if (sm.getText1() != null && !"".equals(sm.getText1())) {

                QueryBuilder qb = buildQbForField(sm.getText1(), sm.getPref1());
                if(qb!=null){
                if ("AND".equals(sm.getOper1()))
                    retVal.must(qb);
                if ("OR".equals(sm.getOper1())) {
                    retVal.should(qb);
                }
                if ("NOT".equals(sm.getOper1()))
                    retVal.mustNot(qb);
            }
            }

            if (sm.getText2() != null && !"".equals(sm.getText2())) {

                QueryBuilder qb = buildQbForField(sm.getText2(), sm.getPref2());
                if (qb != null) {
                    if ("AND".equals(sm.getOper1()))
                        retVal.must(qb);
                    if ("OR".equals(sm.getOper1())) {
                        retVal.should(qb);
                    }
                    if ("NOT".equals(sm.getOper1()))
                        retVal.mustNot(qb);
                }
            }

            if (sm.getText3() != null && !"".equals(sm.getText3())) {

                QueryBuilder qb = buildQbForField(sm.getText3(), sm.getPref3());
                if (qb != null) {
                    if ("AND".equals(sm.getOper2()))
                        retVal.must(qb);
                    if ("OR".equals(sm.getOper2()))
                        retVal.should(qb);
                    if ("NOT".equals(sm.getOper2()))
                        retVal.mustNot(qb);
                }
            }
            if (sm.getText4() != null && !"".equals(sm.getText4())) {

                QueryBuilder qb = buildQbForField(sm.getText4(), sm.getPref4());
                if (qb != null) {
                    if ("AND".equals(sm.getOper3()))
                        retVal.must(qb);
                    if ("OR".equals(sm.getOper3()))
                        retVal.should(qb);
                    if ("NOT".equals(sm.getOper3()))
                        retVal.mustNot(qb);
                }
            }

            if (sm.getText5() != null && !"".equals(sm.getText5())) {

                QueryBuilder qb = buildQbForField(sm.getText5(), sm.getPref5());
                if (qb != null) {
                    if ("AND".equals(sm.getOper4()))
                        retVal.must(qb);
                    if ("OR".equals(sm.getOper4()))
                        retVal.should(qb);
                    if ("NOT".equals(sm.getOper4()))
                        retVal.mustNot(qb);
                }
            }
//            retVal.minimumShouldMatch(1);

            if (sm.getDepartments() != null && sm.getDepartments().size() > 0) {
                for (String dep : sm.getDepartments()) {
                    retVal.must(QueryBuilders.matchQuery("prefixes.OD", dep));
                }
            }

            if (sm.getBranches() != null && sm.getBranches().size() > 0) {
                for (String branch: sm.getBranches()) {
                    // Signatura podlokacija - podlokacija
                    retVal.must(QueryBuilders.matchQuery("prefixes.SL", branch));
                }
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
//        System.out.println(retVal.toString());
        return retVal;
    }

    public static BoolQueryBuilder filterSearch(BoolQueryBuilder queryBuilder, FiltersReq filtersReq) {
        if (queryBuilder == null) return null;
//        BoolQueryBuilder retVal = queryBuilder;
        if (filtersReq == null) return queryBuilder;

        BoolQueryBuilder retVal = QueryBuilders.boolQuery();
        retVal.must(queryBuilder);

        if (filtersReq.getLanguages() != null && filtersReq.getLanguages().size() > 0) {
            BoolQueryBuilder bq = null;
            for (SelectedFilter lan: filtersReq.getLanguages()) {
                if (lan.getItem() != null && lan.isValid() && !lan.getItem().getValue().equals(FiltersReq.MERGE_FILTER_SERBIAN)) {
                    retVal.must(QueryBuilders.matchQuery("prefixes.LA", lan.getItem().getValue()));
                } else {
//                    TODO: If needed make map with filters for merging and their regexes
                    retVal.must(QueryBuilders.regexpQuery("prefixes.LA", "((srp)|(scr)|(scc))"));
                }
            }
        }

        if (filtersReq.getAuthors() != null && filtersReq.getAuthors().size() > 0) {
            for (SelectedFilter e: filtersReq.getAuthors()) {
                if (e.getItem() != null && e.isValid())
                    retVal.must(QueryBuilders.matchQuery("prefixes.authors_raw", e.getItem().getValue()).operator(Operator.AND).analyzer("keyword"));
            }
        }

        if (filtersReq.getSubjects() != null && filtersReq.getSubjects().size() > 0) {
            for (SelectedFilter e: filtersReq.getSubjects()) {
                if (e.getItem() != null && e.isValid())
                    retVal.must(QueryBuilders.matchQuery("prefixes.subjects_raw", e.getItem().getValue()).operator(Operator.AND).analyzer("keyword"));
            }
        }

        if (filtersReq.getLocations() != null && filtersReq.getLocations().size() > 0) {
            for (SelectedFilter e: filtersReq.getLocations()) {
                if (e.getItem() != null && e.isValid())
                    retVal.must(QueryBuilders.matchQuery("prefixes.OD", e.getItem().getValue()));
            }
        }

        if (filtersReq.getSubLocations() != null && filtersReq.getSubLocations().size() > 0) {
            for (SelectedFilter e: filtersReq.getSubLocations()) {
                if (e.getItem() != null && e.isValid())
                    retVal.must(QueryBuilders.matchQuery("prefixes.SL", e.getItem().getValue()));
            }
        }

        if (filtersReq.getPubTypes() != null && filtersReq.getPubTypes().size() > 0) {
            for (SelectedFilter e: filtersReq.getPubTypes()) {
                if (e.getItem() != null && e.isValid())
                    retVal.must(QueryBuilders.matchQuery("prefixes.DT", e.getItem().getValue()));
            }
        }

        if (filtersReq.getPubYears() != null && filtersReq.getPubYears().size() > 0) {
            for (SelectedFilter e: filtersReq.getPubYears()) {
                if (e.getItem() != null && e.isValid())
                    retVal.must(QueryBuilders.matchQuery("prefixes.PY", e.getItem().getValue()));
            }
        }
        return retVal;
    }


    //expand search query
    public static BoolQueryBuilder makeExpandQuery(String prefName, String prefValue) {
        BoolQueryBuilder retVal = new BoolQueryBuilder();
        try {
            if ("".equals(prefName) || "".equals(prefValue))
                return null;

            if ((prefName.equals("IN") && prefValue.length() >= 4) || (AUTOCOMPLETE_PREFIXES.contains(prefName))) {
                retVal.must(QueryBuilders.wildcardQuery("prefixes." + prefName, prefValue + "*"));
            } else {
                retVal.must(QueryBuilders.matchPhrasePrefixQuery("prefixes." + prefName, LatCyrUtils.toLatinUnaccented(prefValue)));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }

        return retVal;
    }



}
