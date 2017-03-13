package Project; /**
 * Created by Sergey on 27.07.2016.
 */

import java.math.BigDecimal;
import java.util.*;

public class Project {
    private int projectId;
    private String projectTitle;
    private String projectDescription;
    private Date startDate;
    private Date endDate;
    private String duration;
    private Curency moneyType;
    private ArrayList<Region> locations;
    private BigDecimal percentSum;
    private Sector sector;
    private ArrayList<Contacts> contacts;
    private ArrayList<Donor> donors;

    public Project() {
    }

    /**
     * Create Constructor
     */

    public Project(int id, String title, String description, Date startDate,
                   Date endDate, Curency valueType, Sector projectSector,
                   ArrayList<Contacts> projectContacts, ArrayList<Region> locations , ArrayList<Donor> donors) {
        if (id < 0) {
            throw new IllegalArgumentException("id cannot be negative number.");
        }
        this.projectId = id;
        if (title == null) {
            throw new IllegalArgumentException("title cannot be null.");
        }
        this.projectTitle = title;
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null.");
        }
        this.projectDescription= description;
        if (startDate == null) {
            //throw new IllegalArgumentException("startDate cannot be null.");
        }
        this.startDate = startDate;

        if (endDate == null) {
           // throw new IllegalArgumentException("endDate cannot be null.");
        }
        this.endDate = endDate;
//        this.duration = getDatesDifference(this.startDate, this.endDate);
        this.duration = daysBetween(startDate, endDate) / 30 + " Months " + daysBetween(startDate, endDate) % 30 + " Days ";
        if (valueType == null) {
            throw new IllegalArgumentException("valueType cannot be null.");
        }
        this.moneyType = valueType;
        if (projectSector == null) {
            projectSector = new Sector();
        }
        this.sector = projectSector;


        if (projectContacts == null) {
            this.contacts = new ArrayList<Contacts>();
        } else {
            this.contacts = projectContacts;
        }
        percentSum = new BigDecimal(0);
        if (locations == null) {
            // throw new IllegalArgumentException("locations cannot be null.");
            this.locations = new ArrayList<Region>();
        } else {
            for (Region i : locations) {
                if (percentSum.add(i.getPercent()).compareTo(new BigDecimal(100)) > 0) {
//                    throw new IllegalArgumentException("percent cannot be greater than 100.");
                } else {
                    percentSum = percentSum.add(i.getPercent());
                }
            }
            this.locations = locations;
        }
        if (donors == null) {
            // throw new IllegalArgumentException("locations cannot be null.");
            this.donors = new ArrayList<Donor>();
        } else {
            this.donors = donors;
        }

    }


//    public Project(int projectId, String projectTitle, String projectDescription, Date startDate,
//                   Date endDate, Curency moneyType, ArrayList<Region> locations,
//                   Sector sector, ArrayList<Contacts> contacts) {
//        if (projectId < 0) {
//            throw new IllegalArgumentException("id cannot be negative number.");
//        } else {
//            this.projectId = projectId;
//        }
//
//        if (projectTitle == null) {
//            throw new IllegalArgumentException("title cannot be null.");
//        } else {
//            this.projectTitle = projectTitle;
//        }
//
//        this.projectDescription = projectDescription;
//
//        if (startDate == null) {
//            throw new IllegalArgumentException("startDate cannot be null.");
//        } else {
//            this.startDate = startDate;
//        }
//
//        if (endDate == null) {
//            throw new IllegalArgumentException("endDate cannot be null.");
//        } else {
//            this.endDate = startDate;
//        }
//
//        if (moneyType == null) {
//            throw new IllegalArgumentException("moneyType cannot be null.");
//        } else {
//            this.moneyType = moneyType;
//        }
//
//        this.duration = daysBetween(startDate, endDate) / 30 + " Months " + daysBetween(startDate, endDate) % 30 + " Days ";
//
//        percentSum = new BigDecimal(0);
//        if (locations == null) {
//            // throw new IllegalArgumentException("locations cannot be null.");
//            this.locations = new ArrayList<Region>();
//        } else {
//            for (Region i : locations) {
//                if (percentSum.add(i.getPercent()).compareTo(new BigDecimal(100)) > 0) {
//                    throw new IllegalArgumentException("percent cannot be greater than 100.");
//                } else {
//                    percentSum = percentSum.add(i.getPercent());
//                }
//            }
//            this.locations = locations;
//        }
//
//        if (sector == null) {
//            throw new IllegalArgumentException("sectors cannot be null.");
//        } else {
//            this.sector = sector;
//        }
//
//        if (contacts == null) {
//            //throw new IllegalArgumentException("contacts cannot be null.");
//            this.contacts = new ArrayList<Contacts>();
//        } else {
//            this.contacts = contacts;
//        }
//    }

    /**
     * Фукнция считающая Duration
     */

    private int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }


    /**
     * Фукнция Добавляющая Локацию
     */

    public void addRegion(Region loc) {
        locations.add(loc);
    }

    /**
     * Фукнция Удаляющая Локацию
     */

    public void deleteLocation(Region loc) {
        locations.remove(loc);
    }

    public void addDonor(Donor don) {
        donors.add(don);
    }



    public void removeDonor(Donor don) {
        donors.remove(don);

    }


    /**
     * Перегрузка функции toString
     */

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectTitle='" + projectTitle + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration='" + duration + '\'' +
                ", moneyType=" + moneyType +
                ", locations=" + locations +
                ", percentSum=" + percentSum +
                ", sectors=" + sector +
                ", contacts=" + contacts +
                '}';
    }

    /**
     * Перегрузка функции equals
     */


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return projectId == project.projectId;
    }

    /**
     * Перегрузка функции hashCode
     */

    @Override
    public int hashCode() {
        return this.projectId;
    }


    /**
     * Gets and Sets
     */

    public int getProjectId() {
        return projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getDuration() {
        return duration;
    }

    public Curency getMoneyType() {
        return moneyType;
    }

    public ArrayList<Region> getLocations() {
        return locations;
    }

    public BigDecimal getPercentSum() {
        return percentSum;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setMoneyType(Curency moneyType) {
        this.moneyType = moneyType;
    }

    public void setLocations(ArrayList<Region> locations) {
        this.locations = locations;
    }

    public void setPercentSum(BigDecimal percentSum) {
        this.percentSum = percentSum;
    }

    public Sector getSector() {
        return sector;
    }

    public ArrayList<Contacts> getContacts() {
        return contacts;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public void setContacts(ArrayList<Contacts> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Donor> getDonors() {
        return donors;
    }

    public void setDonors(ArrayList<Donor> donors) {
        this.donors = donors;
    }
}
