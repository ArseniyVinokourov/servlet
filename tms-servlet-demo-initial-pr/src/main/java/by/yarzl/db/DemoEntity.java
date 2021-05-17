package by.yarzl.db;

// Для работы движков конвертации в JSON необходимо определить сеттеры и геттеры для всех полей,
// даже для тех, которые не используются явно

//кирпичики, из которых сторится наша программа
public class DemoEntity {

    private int id;
    private String name;
    private String surname;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
