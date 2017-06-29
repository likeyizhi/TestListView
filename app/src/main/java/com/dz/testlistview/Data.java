package com.dz.testlistview;

import java.util.List;

/**
 * Created by likey on 2017/5/18.
 */

public class Data {
    private String top;
    private String bottom;
    private List<Children> children;

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    public Data(String top, String bottom, List<Children> children) {
        this.top = top;
        this.bottom = bottom;
        this.children = children;
    }

    public static class Children {
        private int image01;
        private int image02;

        public int getImage01() {
            return image01;
        }

        public void setImage01(int image01) {
            this.image01 = image01;
        }

        public int getImage02() {
            return image02;
        }

        public void setImage02(int image02) {
            this.image02 = image02;
        }

        public Children(int image01, int image02) {
            this.image01 = image01;
            this.image02 = image02;
        }
    }
}
