package com.javadreamteam.shelteranimalbot.model;

import com.javadreamteam.shelteranimalbot.keyboard.ClientStatus;
import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "client_cat")
public class ClientCat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;


    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ClientStatus status;

    @OneToOne
    @JoinColumn(name = "cat_id")
    private Cat cat;

    @Column(name = "report_days")
    private Long reportDays;

    public ClientCat() {
    }

    public ClientCat (String name, Long chatId, String phoneNumber) {
        this.name = name;
        this.chatId = chatId;
        this.phoneNumber = phoneNumber;
    }

    public ClientCat(Long id, String name, String phoneNumber, ClientStatus status) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public ClientCat(Long chatId, String firstName, String phoneNumber) {
        this.chatId = chatId;
        this.name = firstName;
        this.phoneNumber = phoneNumber;
    }

    public Long getReportDays() {
        return reportDays;
    }

    public void setReportDays(Long reportDays) {
        this.reportDays = reportDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientCat clientCat = (ClientCat) o;
        return Objects.equals(id, clientCat.id) && Objects.equals(name, clientCat.name) && Objects.equals(chatId, clientCat.chatId) && Objects.equals(phoneNumber, clientCat.phoneNumber) && Objects.equals(age, clientCat.age) && status == clientCat.status && Objects.equals(cat, clientCat.cat) && Objects.equals(reportDays, clientCat.reportDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, chatId, phoneNumber, age, status, cat, reportDays);
    }

    @Override
    public String toString() {
        return "ClientCat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chatId=" + chatId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", status=" + status +
                ", cat=" + cat +
                ", reportDays=" + reportDays +
                '}';
    }
}
