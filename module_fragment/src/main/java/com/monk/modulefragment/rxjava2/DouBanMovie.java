package com.monk.modulefragment.rxjava2;

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
                ", subjects=" + subjects +
                '}';
    }

    public int count;
    public int start;
    public int total;
    public String title;
    public List<SubjectsBean> subjects;

    public static class SubjectsBean {

        @Override
        public String toString() {
            return "SubjectsBean{" +
                    "rating=" + rating +
                    ", title='" + title + '\'' +
                    ", collect_count=" + collect_count +
                    ", original_title='" + original_title + '\'' +
                    ", subtype='" + subtype + '\'' +
                    ", year='" + year + '\'' +
                    ", images=" + images +
                    ", alt='" + alt + '\'' +
                    ", id='" + id + '\'' +
                    ", genres=" + genres +
                    ", casts=" + casts +
                    ", directors=" + directors +
                    '}';
        }

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

            @Override
            public String toString() {
                return "CastsBean{" +
                        "alt='" + alt + '\'' +
                        ", avatars=" + avatars +
                        ", name='" + name + '\'' +
                        ", id='" + id + '\'' +
                        '}';
            }

            public static class AvatarsBean {
                public String small;
                public String large;
                public String medium;

                @Override
                public String toString() {
                    return "AvatarsBean{" +
                            "small='" + small + '\'' +
                            ", large='" + large + '\'' +
                            ", medium='" + medium + '\'' +
                            '}';
                }
            }
        }

        public static class DirectorsBean {
            public String alt;
            public AvatarsBeanX avatars;
            public String name;
            public String id;

            @Override
            public String toString() {
                return "DirectorsBean{" +
                        "alt='" + alt + '\'' +
                        ", avatars=" + avatars +
                        ", name='" + name + '\'' +
                        ", id='" + id + '\'' +
                        '}';
            }

            public static class AvatarsBeanX {
                public String small;
                public String large;
                public String medium;

                @Override
                public String toString() {
                    return "AvatarsBeanX{" +
                            "small='" + small + '\'' +
                            ", large='" + large + '\'' +
                            ", medium='" + medium + '\'' +
                            '}';
                }
            }
        }
    }
}
