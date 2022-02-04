package ru.platform.learning.mainservice.model;

import java.util.LinkedHashMap;
import java.util.Map;

public enum LessonTopic {
    JAVA_1("Основы Java. "),
    JAVA_2("Объекто-орентированное программирование(ООП). "),
    JAVA_3("Обработка ошибок, исключения, отладка и журналирование. "),
    JAVA_4("Потоки ввода-вывода. "),
    JAVA_5("Дженерики, коллекции и Stream Api. "),
    THREAD_1("Мнопоточность 1 "),
    THREAD_2("Многопоточность 2 "),
    SQL("Основы и практика по SQL "),
    JPA_HIBERNATE("Основы и практика JPA и HIBERNATE "),
    SPRING_1("Spring Core "),
    SPRING_2("Выполнение программных приложений "),
    GIT_TRELLO("Дипломный проект "),
    PATTERN("Основные паттерны Java "),
    ALGORITHMS("Биг О и основные алгоритмы ");

    private final String topic;

    LessonTopic(String topic) {
        this.topic = topic;
    }

    public static Map<String, String> readTopicDescription(){
        Map<String, String> map = new LinkedHashMap<>();
        for (LessonTopic topic:LessonTopic.values()) {
            map.put(topic.getTopic(), TopicDescription.valueOf(topic.name()).topicDescription);
        }
        return map;
    }

    enum TopicDescription{
        JAVA_1("В этом разделе присутствую вопросы и задачи, на понимание базовых конструкции языка Java. <a id='lesson0' href='/user/nav/lessons/0'>Поехали...</a>"),
        JAVA_2("Объектно-ориентированное программирование (ООП) является ведущей парадигмой программирования, и знание основ и принципов ООП необходимо для успешной разработки программного обеспечения и является обязательным для устройства на работу разработчиком в IT-компанию. Но самостоятельное изучение ООП часто сопряжено с большими трудностями для начинающих, многим не удается понять принципы, по которым должна строиться правильная объектно-ориентированная программа. <a id='lesson1' href='/user/nav/lessons/1'>Поехали...</a>"),
        JAVA_3("В этом разделе Вы познакомитесь с вопросами связанными с одной из важных тем \"Обработка ошибок, исключения, отладка и журналирование\". <a id='lesson2' href='/user/nav/lessons/2'>Поехали...</a>"),
        JAVA_4("Этот раздел Вас познакомит с темой \"Потоки ввода-вывода\".<a id='lesson3' href='/user/nav/lessons/3'>Поехали...</a>"),
        JAVA_5("Это обширный раздел, познакомит Вас с \"Дженерики, коллекции и Stream Api\".<a id='lesson4' href='/user/nav/lessons/4'>Поехали...</a>"),
        THREAD_1("В этом разделе мы познакомимся с Вами с процессами, потоками, и пройдемся по основам многопоточного программирования на языке Java.<a id='lesson5' href='/user/nav/lessons/5'>Поехали...</a>"),
        THREAD_2("Этот раздел мы посвятим узучению библиотеки \"java.util.concurrent\".<a id='lesson6' href='/user/nav/lessons/6'>Поехали...</a>"),
        SQL("Этот раздел откроет небольшой цикл, посвященный азам взаимодействия с базами данных (БД) в Java и введению в SQL.<a id='lesson7' href='/user/nav/lessons/7'>Поехали...</a>"),
        JPA_HIBERNATE("Раздел посвящен изучению технологии JPA и её использованию на примере реализации Hibernate.<a id='lesson8' href='/user/nav/lessons/8'>Поехали...</a>"),
        SPRING_1("Раздел посвящен изучению основ одной из востребованных технологии, Spring Framework.<a id='lesson9' href='/user/nav/lessons/9'>Поехали...</a>"),
        SPRING_2("В этом разделе мы начнем изучать \"кишки\" Spring Framework.<a id='lesson10' href='/user/nav/lessons/10'>Поехали...</a>"),
        GIT_TRELLO("Этот раздел мы с Вами посвятим написанию небольшого приложения, познакомимся с GIT и другими инструментами для разработки приложений применяемых в командах разработки.<a id='lesson11' href='/user/nav/lessons/11'>Поехали...</a>"),
        PATTERN("Этот раздел посвящен пониманию и изучению основных паттернов применяемых в Java.<a id='lesson12' href='/user/nav/lessons/12'>Поехали...</a>"),
        ALGORITHMS("Раздел посвящен пониманию применения основных алгоритмов, понимание Биг О.<a id='lesson13' href='/user/nav/lessons/13'>Поехали...</a>");

        final String topicDescription;

        TopicDescription(String s) {
            topicDescription = s;
        }

    }

    public String getTopic() {
        return topic;
    }

}
