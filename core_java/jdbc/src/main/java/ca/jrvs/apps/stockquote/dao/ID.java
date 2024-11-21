package ca.jrvs.apps.stockquote.dao;

public class ID {

    private long id;

    public ID(long id) {
        this.id = id;
    }

    public int toInt() {
        return (int) id;
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


}
