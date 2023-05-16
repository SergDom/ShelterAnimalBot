package com.javadreamteam.shelteranimalbot.model;

import com.javadreamteam.shelteranimalbot.keyboard.ClientStatus;
import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "client_dog")
public class ClientDog {

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
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @Column(name = "report_days")
    private Long reportDays;


    public ClientDog() {
    }

    public ClientDog(Long chatId, String name, String phoneNumber) {
        this.chatId = chatId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public ClientDog(Long id, String name, String phoneNumber, ClientStatus status) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
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

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDog clientDog = (ClientDog) o;
        return Objects.equals(id, clientDog.id) && Objects.equals(name, clientDog.name) && Objects.equals(chatId, clientDog.chatId) && Objects.equals(phoneNumber, clientDog.phoneNumber) && Objects.equals(age, clientDog.age) && status == clientDog.status && Objects.equals(dog, clientDog.dog) && Objects.equals(reportDays, clientDog.reportDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, chatId, phoneNumber, age, status, dog, reportDays);
    }

    @Override
    public String toString() {
        return "ClientDog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chatId=" + chatId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", status=" + status +
                ", dog=" + dog +
                ", reportDays=" + reportDays +
                '}';
    }
}
