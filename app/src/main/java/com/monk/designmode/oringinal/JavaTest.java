package com.monk.designmode.oringinal;

import java.util.ArrayList;

/**
 * @author monk
 * @date 2019-01-10
 */
public class JavaTest {
    public static void main(String[] args) {
        WordDocument original=new WordDocument();
        original.setText("original");
        ArrayList<String> list = new ArrayList<>();
        list.add("original001");
        list.add("original002");
        list.add("original003");
        original.setImageList(list);
        original.show();

        // 深拷贝
        ArrayList<String> listClone = (ArrayList<String>) list.clone();
        WordDocument clone = original.clone();
        clone.show();
        clone.setText("clone");
        listClone.add("clone004");
        clone.setImageList(listClone);
        clone.show();
    }
}
