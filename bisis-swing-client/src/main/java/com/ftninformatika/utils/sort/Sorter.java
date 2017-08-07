package com.ftninformatika.utils.sort;

import com.ftninformatika.utils.string.StringUtils;

import java.util.Vector;

/**
 * Created by Petar on 8/7/2017.
 */
public class Sorter
{
    public Sorter() {}

    public static String qsort(String s)
    {
        byte[] b = StringUtils.getLowerBytes(s);
        qsort(0, b.length - 1, b);
        return StringUtils.getStringLower(b);
    }

    public static void qsort(int[] niz)
    {
        qsort(0, niz.length - 1, niz);
    }

    public static void qsort(byte[] niz)
    {
        qsort(0, niz.length - 1, niz);
    }

    public static void qsort(String[] niz)
    {
        qsort(0, niz.length - 1, niz);
    }

    public static void qsort(Sortable[] array)
    {
        qsort(0, array.length - 1, array);
    }

    public static Vector sortVector(Vector v)
    {
        Vector retVal = new Vector();
        String[] niz = (String[])null;
        String[] sniz = (String[])null;

        niz = new String[v.size()];
        v.copyInto(niz);
        qsort(niz);
        for (int i = 0; i < niz.length; i++)
            retVal.addElement(niz[i]);
        return retVal;
    }




    public static void qsort(int d, int g, byte[] a)
    {
        if (d >= g) {
            return;
        }
        if (d + 1 == g) {
            if (a[d] > a[g])
            {
                byte k = a[d];
                a[d] = a[g];
                a[g] = k;
            }
            return;
        }


        int j = d;
        for (int i = d + 1; i <= g; i++) {
            if (a[i] <= a[d])
            {
                j++;
                byte k = a[j];
                a[j] = a[i];
                a[i] = k;
            }
        }
        byte k = a[d];
        a[d] = a[j];
        a[j] = k;

        qsort(d, j - 1, a);
        qsort(j + 1, g, a);
    }




    public static void qsort(int d, int g, String[] a)
    {
        if (d >= g) {
            return;
        }
        if (d + 1 == g) {
            if (a[d].compareTo(a[g]) > 0)
            {
                String k = a[d];
                a[d] = a[g];
                a[g] = k;
            }
            return;
        }


        int j = d;
        for (int i = d + 1; i <= g; i++) {
            if (a[i].compareTo(a[d]) <= 0)
            {
                j++;
                String k = a[j];
                a[j] = a[i];
                a[i] = k;
            }
        }
        String k = a[d];
        a[d] = a[j];
        a[j] = k;

        qsort(d, j - 1, a);
        qsort(j + 1, g, a);
    }




    public static void qsort(int d, int g, int[] a)
    {
        if (d >= g) {
            return;
        }
        if (d + 1 == g) {
            if (a[d] > a[g])
            {
                int k = a[d];
                a[d] = a[g];
                a[g] = k;
            }
            return;
        }


        int j = d;
        for (int i = d + 1; i <= g; i++) {
            if (a[i] <= a[d])
            {
                j++;
                int k = a[j];
                a[j] = a[i];
                a[i] = k;
            }
        }
        int k = a[d];
        a[d] = a[j];
        a[j] = k;

        qsort(d, j - 1, a);
        qsort(j + 1, g, a);
    }




    public static void qsort(int d, int g, Sortable[] a)
    {
        if (d >= g) {
            return;
        }
        if (d + 1 == g) {
            if (a[d].compareTo(a[g]) > 0)
            {
                Sortable k = a[d];
                a[d] = a[g];
                a[g] = k;
            }
            return;
        }


        int j = d;
        for (int i = d + 1; i <= g; i++) {
            if (a[i].compareTo(a[d]) <= 0)
            {
                j++;
                Sortable k = a[j];
                a[j] = a[i];
                a[i] = k;
            }
        }
        Sortable k = a[d];
        a[d] = a[j];
        a[j] = k;

        qsort(d, j - 1, a);
        qsort(j + 1, g, a);
    }
}