package com.monk.designmode.oringinal;

import java.util.List;

/**
 * cloneable 是一个空接口
 * @author monk
 * @date 2019-01-10
 */
public class WordDocument implements Cloneable {
    private String text;
    private List<String> imageList;

    public void setText(String text) {
        this.text = text;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getText() {
        return text;
    }

    public List<String> getImageList() {
        return imageList;
    }

    @Override
    protected WordDocument clone()  {
        try {
            WordDocument clone = (WordDocument) super.clone();
            clone.imageList = this.imageList;
            clone.text=this.text;
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "{" +
            "text='" + text + '\'' +
                    ", imageList=" + imageList +
                    '}';
    }

    public void show() {
        System.out.println(hashCode()+toString());
    }
}
