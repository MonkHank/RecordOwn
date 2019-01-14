package com.monk.rxjava2;

import java.util.List;

/**
 * @author monk
 * @date 2019-01-03
 */
public class DouBanMovie {

    @Override
    public String toString() {
        return "DouBanMovie{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", title='" + title + '\'' +
                '}';
    }

    public int count;
    public int start;
    public int total;
    public String title;
    public List<SubjectsBean> subjects;

    public static class SubjectsBean {
        public RatingBean rating;
        public String title;
        public int collect_count;
        public String original_title;
        public String subtype;
        public String year;
        public ImagesBean images;
        public String alt;
        public String id;
        public List<String> genres;
        public List<CastsBean> casts;
        public List<DirectorsBean> directors;

        public static class RatingBean {
            public int max;
            public double average;
            public String stars;
            public int min;
        }


        public static class ImagesBean {
            public String small;
            public String large;
            public String medium;

        }

        public static class CastsBean {
            public String alt;
            public AvatarsBean avatars;
            public String name;
            public String id;

            public static class AvatarsBean {
                public String small;
                public String large;
                public String medium;

            }
        }

        public static class DirectorsBean {
            public String alt;
            public AvatarsBeanX avatars;
            public String name;
            public String id;

            public static class AvatarsBeanX {
                public String small;
                public String large;
                public String medium;
            }
        }
    }
}
