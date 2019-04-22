package com.liub.baselibrary.net;

/**
 * Create by liub on 2019/4/8
 * Describe:
 */
public class BaseComplexResult<T> {
    private int code;
    private String msg;
    private DataBean<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean<T> getData() {
        return data;
    }

    public void setData(DataBean<T> data) {
        this.data = data;
    }

    public class DataBean<D> {
        private int size;
        private int count;
        private D content;
        private int totalPages;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public D getContent() {
            return content;
        }

        public void setContent(D content) {
            this.content = content;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
    }

}
