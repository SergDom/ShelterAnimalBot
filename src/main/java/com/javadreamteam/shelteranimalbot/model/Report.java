package com.javadreamteam.shelteranimalbot.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "report")
public class Report {

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
     * Количество дней для отчета
     */
    private long days;

    /**
     * Фото
     */

    private byte[] photo;

    private LocalDate lastMessage;

    /**
     * Фото петомца
     */
//    @OneToMany(mappedBy = "report")
//    private Collection<ReportPhoto> reportPhoto;
    /**
     * id питомца
     */
    @ManyToOne
    @JoinColumn(name = "client_dog_id")
    private ClientDog clientDog;

    @ManyToOne
    @JoinColumn(name = "client_cat_id")
    private ClientCat clientCat;

    public Report() {
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

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public ClientDog getClientDog() {
        return clientDog;
    }

    public void setClientDog(ClientDog clientDog) {
        this.clientDog = clientDog;
    }

    public ClientCat getClientCat() {
        return clientCat;
    }

    public void setClientCat(ClientCat clientCat) {
        this.clientCat = clientCat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return days == report.days && Objects.equals(id, report.id) && Objects.equals(chatId, report.chatId) && Objects.equals(ration, report.ration) && Objects.equals(health, report.health) && Objects.equals(habits, report.habits) && Arrays.equals(photo, report.photo) && Objects.equals(lastMessage, report.lastMessage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, chatId, ration, health, habits, days, lastMessage);
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
                ", days=" + days +
                ", photo=" + Arrays.toString(photo) +
                ", lastMessage=" + lastMessage +
                ", clientDog=" + clientDog +
                '}';
    }
}

