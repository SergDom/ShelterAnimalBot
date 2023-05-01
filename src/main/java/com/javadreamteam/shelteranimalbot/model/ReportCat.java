package com.javadreamteam.shelteranimalbot.model;

import com.javadreamteam.shelteranimalbot.keyboard.ReportStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "report_cat")
public class ReportCat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * Идентификатор записи, primary key
     */
    private Long id;

    /**
     * Идентификатор чата хозяина животного, отправившего отчет
     */
    private Long chatId;
    /**
     * Рацион животного
     */
    private String ration;
    /**
     * Здоровье животного
     */
    private String health;
    /**
     * Привычки животного
     */
    private String habits;

    /**
     * Общая информация
     */
    private String info;

    /**
     * Фото в отчете
     */

    private byte[] photo;

    /**
     * Дата отправки отчета
     */
    private LocalDate lastMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus reportStatus;

    /**
     * id питомца
     */
    @ManyToOne
    @JoinColumn(name = "client_cat_id")
    private ClientCat clientCat;


    public ReportCat() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDate getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(LocalDate lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getRation() {
        return ration;
    }

    public void setRation(String ration) {
        this.ration = ration;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getHabits() {
        return habits;
    }

    public void setHabits(String habits) {
        this.habits = habits;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public ClientCat getClientCat() {
        return clientCat;
    }

    public void setClientDog(ClientCat clientCat) {
        this.clientCat = clientCat;
    }


    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportCat reportCat = (ReportCat) o;
        return Objects.equals(id, reportCat.id) && Objects.equals(chatId, reportCat.chatId) && Objects.equals(ration, reportCat.ration) && Objects.equals(health, reportCat.health) && Objects.equals(habits, reportCat.habits) && Objects.equals(info, reportCat.info) && Arrays.equals(photo, reportCat.photo) && Objects.equals(lastMessage, reportCat.lastMessage) && reportStatus == reportCat.reportStatus && Objects.equals(clientCat, reportCat.clientCat);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, chatId, ration, health, habits, info, lastMessage, reportStatus, clientCat);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", ration='" + ration + '\'' +
                ", health='" + health + '\'' +
                ", habits='" + habits + '\'' +
                ", info='" + info + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", lastMessage=" + lastMessage +
                '}';
    }
}


