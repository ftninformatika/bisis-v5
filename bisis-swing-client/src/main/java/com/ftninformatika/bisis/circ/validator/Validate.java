package com.ftninformatika.bisis.circ.validator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.options.OptionsFrame;
import com.ftninformatika.bisis.circ.view.User;
import com.ftninformatika.utils.Messages;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.apache.log4j.Logger;


public class Validate {

	//private static ResourceBundle res = ResourceBundle.getBundle("circ_validator.validatorResources");
	private static ResourceBundle res;
	private static final String BUNDLE_NAME = "circ_validator/validatorResources.properties";
    private static Logger log = Logger.getLogger(Validate.class);


	static  {
		try {
			Reader reader = new InputStreamReader(Messages.class.getClassLoader().getResourceAsStream(BUNDLE_NAME), "UTF-8");
			res = new PropertyResourceBundle(reader);
		}catch (Exception e){
			e.printStackTrace();
		}
	}




	public static String validate(Object bean, ValidatorResources resources, String formName){
		try{
			StringBuilder errorMsg = new StringBuilder();
			boolean success = true;

			Validator validator = new Validator(resources, formName);
			validator.setParameter(Validator.BEAN_PARAM, bean);
			ValidatorResults results = null;
			results = validator.validate();

			Form form = resources.getForm(Locale.getDefault(), formName);
			Iterator propertyNames = results.getPropertyNames().iterator();
			while (propertyNames.hasNext()) {
				String propertyName = (String) propertyNames.next();
				Field field = form.getField(propertyName);
				String prettyFieldName = res.getString(field.getArg(0).getKey());
				ValidatorResult result = results.getValidatorResult(propertyName);
				Iterator keys = result.getActions();
				while (keys.hasNext()) {
					String actName = (String) keys.next();
					ValidatorAction action = resources.getValidatorAction(actName);
					if (!result.isValid(actName)) {
						success = false;
						String message = res.getString(action.getMsg());
						if (action.getMsg().equals("invalid.date")){
							Object[] args = { prettyFieldName, field.getVarValue("datePattern") };
							errorMsg.append("\n"+ MessageFormat.format(message, args));
						}else{
							Object[] args = { prettyFieldName };
							errorMsg.append("\n"+ MessageFormat.format(message, args));
						}
					}
				}
			}

			if (success) {
				return null;
			} else {
				return errorMsg.toString();
			}

		}catch(Exception e){
			log.error(e);
			return "Error occured";
		}
	}

	public static String validateUser(User bean){
		try{
			InputStream xmlStream;
			String xml = Cirkulacija.getApp().getUserManager().getValidatorFile();
			if (xml != null){
				xmlStream = new ByteArrayInputStream(xml.getBytes());
			} else {
				xmlStream = bean.getClass().getResourceAsStream("/user-bean-validator.xml");
			}
			ValidatorResources resources = new ValidatorResources(xmlStream);
			String temp1 = validate(bean.getUserData(), resources, "userData");
			String temp2 = validate(bean.getMmbrship(), resources, "mmbrship");
			if (temp1 == null && temp2 == null){
				return null;
			}else if (temp1 == null){
				return temp2;
			}else if (temp2 == null){
				return temp1;
			}else {
				return temp1 + temp2;
			}
		}catch(Exception e){
			log.error(e);
			return "Error occured";
		}

	}

	public static String validateOptions(OptionsFrame bean){
		try{
			InputStream xmlStream = bean.getClass().getResourceAsStream("/com/gint/app/bisis4/client/circ/validator/options-validator.xml");
			ValidatorResources resources = new ValidatorResources(xmlStream);
			return validate(bean, resources, "options");
		}catch(Exception e){
			log.error(e);
			return "Error occured";
		}

	}

	public static void fixLabels(Object bean, ValidatorResources resources, String formName){
		try{
			Validator validator = new Validator(resources, formName);
			validator.setParameter(Validator.BEAN_PARAM, bean);
			validator.validate();
		}catch(Exception e){
			log.error(e);
		}
	}

	public static void fixUserLabels(User bean){
		try{
			InputStream xmlStream;
			String xml = Cirkulacija.getApp().getUserManager().getValidatorFile();
			if (xml != null){
				xmlStream = new ByteArrayInputStream(xml.getBytes());
			} else {
				xmlStream = bean.getClass().getResourceAsStream("/user-bean-validator.xml");
			}
			ValidatorResources resources = new ValidatorResources(xmlStream);
			fixLabels(bean.getUserData(), resources, "userData");
			fixLabels(bean.getMmbrship(), resources, "mmbrship");
		}catch(Exception e){
			log.error(e);
		}
	}
}