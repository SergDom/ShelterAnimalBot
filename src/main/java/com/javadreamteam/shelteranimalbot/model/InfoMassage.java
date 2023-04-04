package com.javadreamteam.shelteranimalbot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class InfoMassage {

    @Id
    private String tag;
    private String text;

    public InfoMassage() {
    }

    public InfoMassage(String tag, String text) {
        this.tag = tag;
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoMassage that = (InfoMassage) o;
        return Objects.equals(tag, that.tag) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, text);
    }
}
