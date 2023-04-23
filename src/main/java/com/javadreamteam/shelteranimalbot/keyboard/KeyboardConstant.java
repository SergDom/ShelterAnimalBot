package com.javadreamteam.shelteranimalbot.keyboard;
/**
 * Класс перечисление. Для кнопок клавиатур бота.
 * содержит конструктор и поля для указания сообщения по нажатию кнопки и ее названия.
 */
public class KeyboardConstant {


    public static final String START = "/start";


    public static final String WELCOME = ", Приветствую! Выберете нужный пункт меню!";

    public static final String INFO_ABOUT_BOT =  "Информация о возможностях бота \n- Бот может показать информацию о приюте \n" +
            "- Покажет какие документы нужны \n- Бот может принимать ежедневный отчет о питомце\n" +
            "- Может передать контактные данные волонтерам для связи \n" + "";
    public static final String SHELTER_ABOUT  = "Информация о приюте: ...";

    public static final String SHELTER_CONTACTS = "Адрес приюта: ...";

    public static final String SHELTER_RULES  = "Правила поведения в приюте, для удобства, изображены на картинке";

    public static final String DOCUMENTS = "Из неообходимых документов Вам потребуется только ПАСПОРТ\n" +
            "                \n Но прежде чем вы решитесь на такой ответсвенный шаг\n" +
            "                \"просим Вас ознакомиться с информацие в прикрепленном документе";

    public static final String REFUSE  = "12 причин отказа, будьте готовы !";
    public static final String TRANSPORT  = "Правила перевозки в приложенной памятке";
    public static final String CYNOLOGIST  = "Ознакомьтесь пожалуйста с рекомендациями кинологов в приложенном файле";
    public static final String CYNOLOGIST_LIST = "Здесь мы собрали рекомендации как выбрать кинолога для вашего питомца";
    public static final String PUPPY  = "Ознакомьтесь пожалуйста с правилами ухода за щенком";
    public static final String ADULT  = "Ознакомьтесь пожалуйста с правилами ухода за взрослым питомцем";
    public static final String DISABLE_PET  = "Рекомендации ухода за питомцем c ограничениями";

    public static final String CALL_VOLUNTEERS = "Волонтер вам поможет, мы перешлем ему Ваш запрос !";

    public static final String HOW_TAKE_A_PET = "....";


    public static final String REPORT_LIST = """
            ЗАГРУЗИТЕ ОТЧЕТ В ФОРМАТЕ:\s
            \s
            Рацион: Данные о рационе\s
            Информация: Общая информация\s
            Привычки: Данные об изменении привычек\s
            Не забудьте загрузить фото к отчету !""";





    public final static String CONTACT_TELEGRAM_USERNAME_TEXT = "Пожалуйста свяжитесь с пользователем %s. Ему нужна помощь.";
    public final static String NO_VOLUNTEERS_TEXT = "На данный момент нет свободных волонтеров.";
}
