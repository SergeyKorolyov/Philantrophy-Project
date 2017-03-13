package Project;

/**
 * Created by Sergey on 29.07.2016.
 */

public class Contacts {
    private int id;
    private String firstName;
    private String lastName;
    private String organisation;
    private String e_mail;
    private String phone;


    /**
     * Определение конструктора со всеми подлежащими проверками
     */

    public Contacts(int id, String firstName, String lastName, String organisation, String e_mail, String phone) {
        if (id < 0) {
            throw new IllegalArgumentException("id cannot be negative number.");
        } else {
            this.id = id;
        }
        if (firstName == null) {
            throw new IllegalArgumentException("firstName cannot be null.");
        } else {
            this.firstName = firstName;
        }
        if (lastName == null) {
            throw new IllegalArgumentException("lastName cannot be null.");
        } else {
            this.lastName = lastName;
        }
        if (organisation == null) {
            throw new IllegalArgumentException("organisation cannot be null.");
        } else {
            this.organisation = organisation;
        }
        if (e_mail == null) {
            throw new IllegalArgumentException("e_mail cannot be null.");
        } else {
            this.e_mail = e_mail;
        }
        if (phone == null) {
            throw new IllegalArgumentException("phone cannot be null.");
        } else {
            this.phone = phone;
        }
    }

    public Contacts() {
    }

    /**
     * Перегрузка функции equals . Сравнение происходит по номеру id .
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacts contact = (Contacts) o;

        return id == contact.id;

    }

    /**
     * Перегрузка функции hashCode . Хещирование происходит по номеру id .
     */

    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Перегрузка функции toString
     */

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", organisation='" + organisation + '\'' +
                ", eMail='" + e_mail + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    /**
     * Определение функций получения знаячений и присваивания занчений со всеми подлежащими проверками.
     */

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("firstName cannot be null.");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("lastName cannot be null.");
        }
        this.lastName = lastName;
    }

    public String  getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String  organisation) {
        if (organisation == null) {
            throw new IllegalArgumentException("organisation cannot be null.");
        }
        this.organisation = organisation;
    }

    public String geteMail() {
        return e_mail;
    }

    public void seteMail(String eMail) {
        if (eMail == null) {
            throw new IllegalArgumentException("e_mail cannot be null.");
        }
        this.e_mail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("phone cannot be null.");
        }
        this.phone = phone;
    }
}

