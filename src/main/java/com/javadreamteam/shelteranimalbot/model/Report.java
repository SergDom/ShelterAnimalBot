package com.javadreamteam.shelteranimalbot.model;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "report")
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
     * Путь до файла
     */
    private String filePath;
    /**
     * Размер файла
     */
    private long fileSize;

    private Date lastMessage;

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

    public Report() {
    }

    public Date getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Date lastMessage) {
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}

