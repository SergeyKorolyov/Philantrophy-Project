package Doa;

import Project.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Currency.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


public class Connect {
    private Connection con = null;
    private final String url = "jdbc:sqlserver://";
    private final String serverName = "localhost";
    private final String portNumber = "1433";
    private final String databaseName = "Philantrophy Project";
    private final String userName = "Sergey";
    private final String password = "1";
    private final String selectMethod = "cursor";

    public Connect() {
    }

    private String getConnectionUrl() {
        return "jdbc:sqlserver://localhost:1433;databaseName=Homework2;selectMethod=cursor;";
    }

    private Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.con = DriverManager.getConnection(this.getConnectionUrl(), "Anun", "1");
            if (this.con != null) {
                System.out.println("Connection Successful!");
            }
        } catch (SQLException var2) {
            var2.printStackTrace();
            System.out.println("Error Trace in getConnection() : " + var2.getMessage());
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            System.out.println("Error during load driver : " + var3.getMessage());
        }

        return this.con;
    }

    public void displayDbProperties() {
        DatabaseMetaData dm = null;
        ResultSet rs = null;

        try {
            this.con = this.getConnection();
            if (this.con != null) {
                dm = this.con.getMetaData();
                System.out.println("Driver Information");
                System.out.println("\tDriver Name: " + dm.getDriverName());
                System.out.println("\tDriver Version: " + dm.getDriverVersion());
                System.out.println("\nDatabase Information ");
                System.out.println("\tDatabase Name: " + dm.getDatabaseProductName());
                System.out.println("\tDatabase Version: " + dm.getDatabaseProductVersion());
                System.out.println("Avalilable Catalogs ");
                rs = dm.getCatalogs();

                while (rs.next()) {
                    System.out.println("\tcatalog: " + rs.getString(1));
                }

                rs.close();
                rs = null;
                this.closeConnection();
            } else {
                System.out.println("Error: No active Connection");
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        dm = null;
    }

    private void printUsersList() {
        try {
            Connection e = this.getConnection();
            Throwable var2 = null;

            try {
                String sql = "SELECT ProjectID, ProjectTitle FROM Project";
                PreparedStatement ps = e.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String lastName = rs.getString("ProjectTitle");
                    System.out.println(lastName + "\n");
                }

                rs.close();
                rs = null;
                this.closeConnection();
            } catch (Throwable var15) {
                var2 = var15;
                throw var15;
            } finally {
                if (e != null) {
                    if (var2 != null) {
                        try {
                            e.close();
                        } catch (Throwable var14) {
                            var2.addSuppressed(var14);
                        }
                    } else {
                        e.close();
                    }
                }

            }
        } catch (SQLException var17) {
            var17.printStackTrace();
        }

    }

    /**
     * Загружаем Дистрикты с Базы данных по провинции
     */

    public List<District> loadDistricts(int provinceId) {
        List<District> districtList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "SELECT Districts.DistrictId, DistrictsName, ProvinceID, PercentNum FROM ProjectDistrict\n" +
                    "inner join Districts on ProjectDistrict.DistrictID = Districts.DistrictID\n" +
                    "where ProvinceID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, provinceId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                District district = new District(rs.getInt("DistrictID"), rs.getString("DistrictsName"),
                        rs.getInt("ProvinceID"), rs.getBigDecimal("PercentNum"));

                districtList.add(district);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return districtList;
    }

    /**
     * Загружаем Провинции с Базы данных по Региону
     */

    public List<Province> loadProvinces(int regionId) {
        List<Province> provinceList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "Select ProvinceID, ProvinceName, RegionID from Provinces\n" +
                    "where RegionID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, regionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                List<District> districtList = loadDistricts(rs.getInt("ProvinceID"));
                Province province = new Province(rs.getInt("ProvinceID"), rs.getString("ProvinceName"),
                        rs.getInt("RegionID"), (ArrayList<District>) districtList);

                provinceList.add(province);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provinceList;
    }

    /**
     * Загружаем Регионы с Базы данных по Проекту
     */

    public List<Region> loadRegions(int projectId) {
        List<Region> regionList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "select Regions.RegionID, RegionName from ProjectDistrict\n" +
                    "inner join Project on Project.ProjectID = ProjectDistrict.ProjectID\n" +
                    "inner join Districts on ProjectDistrict.DistrictID = Districts.DistrictID\n" +
                    "inner join Provinces on Provinces.ProvinceID = Districts.ProvinceID\n" +
                    "inner join Regions on Regions.RegionID = Provinces.RegionID";
            PreparedStatement ps;
            if (projectId != -1) {
                sql += "where ProjectID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, projectId);
            } else {
                ps = con.prepareStatement(sql);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                List<Province> provinceList = loadProvinces(rs.getInt("RegionId"));
                Region region = new Region(rs.getInt("RegionId"), rs.getString("RegionName"),
                        (ArrayList<Province>) provinceList);
                regionList.add(region);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regionList;
    }

    /**
     * Загружаем SubSector-ы с Базы данных по SectorId
     */

    public List<SubSector> loadSubSectors(int SectorId) {
        List<SubSector> subSectorList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "SELECT SubSectorID, SubSectorName, Sectors.SectorID FROM SubSectors\n" +
                    "inner join Sectors on Sectors.SectorID = SubSectors.SectorID\n" +
                    "where Sectors.SectorID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, SectorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SubSector subSector = new SubSector(rs.getInt("SubSectorID"), rs.getString("SubSectorName"),
                        rs.getInt("SectorID"));

                subSectorList.add(subSector);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subSectorList;
    }

    /**
     * Загружаем Sector-ы с Базы данных\
     */

    public List<Sector> loadSectors(int projectId) {
        List<Sector> sectorList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql;
            ResultSet rs;
            PreparedStatement ps;
            if (projectId == -1) {
                sql = "Select SectorId, SectorName from Sectors";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Sector sector = new Sector(rs.getInt("SectorId"), rs.getString("SectorName"), null);

                    sectorList.add(sector);
                }
            } else {
                sql = "select Sectors.SectorID, SectorName, SubSectors.SubSectorID, SubSectorName from Project\n" +
                        "inner join SubSectors on SubSectors.SubSectorID = Project.SubSectorID\n" +
                        "inner join Sectors on Sectors.SectorID = SubSectors.SectorID";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Sector sector = new Sector(rs.getInt("SectorId"), rs.getString("SectorName"),
                            new SubSector(rs.getInt("SubSectorID"), rs.getString("SubSectorName"), rs.getInt("SectorId")));

                    sectorList.add(sector);
                }
            }

            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectorList;
    }

    /**
     * Загружаем Контакты с Базы данных по ProjectId
     */

    public List<Contacts> loadContacts(int projectId) {
        List<Contacts> contactList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "Select Contacts.ContactId, FirstName, LastName, Organisation, Email, Phone from Contacts\n" +
                    "inner join ContactsProjects on ContactsProjects.ContactID = Contacts.ContactId\n" +
                    "where ProjectID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Contacts contact = new Contacts(rs.getInt("ContactId"), rs.getString("FirstName"),
                        rs.getString("LastName"), rs.getString("Organisation"),
                        rs.getString("Email"), rs.getString("Phone"));
                contactList.add(contact);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }


    /**
     * Загружаем Donors с Базы данных
     */

    public List<Donor> loadDonors(int projectID) {
        List<Donor> donorList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql;
            PreparedStatement ps;
            if (projectID == -1) {
                sql = "Select DonorID, DonorName from AvailableDonors";
                ps = con.prepareStatement(sql);
            } else {
                sql = "Select AvailableDonors.DonorID, DonorName from AvailableDonors\n" +
                        "inner join SelectedDonors on SelectedDonors.DonorID = AvailableDonors.DonorID\n" +
                        "inner join Project on Project.ProjectID = SelectedDonors.ProjectID\n" +
                        "where SelectedDonors.ProjectID = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, projectID);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Donor donor = new Donor(rs.getInt("DonorID"), rs.getString("DonorName"));

                donorList.add(donor);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donorList;
    }


    /**
     * Загружаем Project с Базы данных
     */

    public List<Project> loadProjects() {
        List<Project> projectList = new ArrayList<>();
        try (Connection con = this.getConnection()) {
            String sql = "Select ProjectID, ProjectTitle, ProjectDescription, StartDate, EndDate, TotalAmount, \n" +
                    "TotalAmountTypeID, AmountName, AmountToDram, Sectors.SectorId, SectorName from Project\n" +
                    "inner join SubSectors on SubSectors.SubSectorID = Project.SubSectorID\n" +
                    "inner join Sectors on Sectors.SectorID =SubSectors.SectorID\n" +
                    "inner join AmountType on AmountType.AmountTypeID = project.TotalAmountTypeID";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Project project = new Project(rs.getInt("ProjectID"), rs.getString("ProjectTitle"),
                        rs.getString("ProjectDescription"), rs.getDate("StartDate"), rs.getDate("EndDate"),
                        new Curency(rs.getInt("TotalAmountTypeID"), rs.getString("AmountName"),
                                rs.getBigDecimal("AmountToDram"), rs.getBigDecimal("TotalAmount")),
                        new Sector(rs.getInt("SectorId"), rs.getString("SectorName"), null),
                        (ArrayList<Contacts>) loadContacts(rs.getInt("ProjectID")),
                        (ArrayList<Region>) loadRegions(rs.getInt("ProjectID")),
                        (ArrayList<Donor>) loadDonors(rs.getInt("ProjectId")));

                projectList.add(project);
            }
            rs.close();
            rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

    /**
     * Удаляем Сектор по SectorId
     */

    public void deleteSectorById(int sectorId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Sectors where SectorID = " + sectorId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем все Сектора
     */

    public void deleteSectorsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Sectors";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Изменяем Имя Сектора чей SectorId == id
     */

    public void updateSector(int id, String name) {
        try (Connection con = this.getConnection()) {
            String sql = "update Sectors\n" +
                    "set SectorName = '" + name + "'" +
                    "\nwhere SectorId = " + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем SubSector по id
     */

    public void deleteSubSectorById(int subSectorId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from SubSectors where SubSectorID = " + subSectorId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем все SubSector
     */

    public void deleteSubSectorsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from SubSectors";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Изменяем Имя SubSector чей SubSectorId == id
     */

    public void updateSubSector(int id, String name) {
        try (Connection con = this.getConnection()) {
            String sql = "update SubSectors\n" +
                    "set SubSectorName = '" + name + "'" +
                    "\nwhere SubSectorsId = " + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем Контакт по id
     */

    public void deleteContactById(int contactID) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Contacts where ContactID = " + contactID;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем  все Контакты
     */

    public void deleteContactsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Contacts";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Изеняем Часть или весь контакт
     */
    public void updateContact(int id, String firtsName, String lastName,
                              Organisation organisation, String mail, String phone) {
        try (Connection con = this.getConnection()) {
            String sql = "update Contacts\nset ";
            if (firtsName != null) {
                sql += "FirstName = '" + firtsName + "',\n";
            }
            if (lastName != null) {
                sql += "lastName = '" + lastName + "',\n";
            }
            if (organisation != null) {
                sql += "organisation = '" + organisation.getName() + "',\n";
            }
            if (mail != null) {
                sql += "Email = '" + mail + "',\n";
            }
            if (phone != null) {
                sql += "Phone = '" + phone + "',\n";
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += "\nwhere ContactID = " + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем ТипДенег по id
     */

    public void deleteAmountTypeById(int amountTypeId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from AmountType where AmountTypeId = " + amountTypeId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем все Типы Денег
     */

    public void deleteAmountTypesAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from AmountType";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Изменяем ТипДенег
     */

    public void updateAmountType(int id, String amountName, BigDecimal amountToDram) {
        try (Connection con = this.getConnection()) {
            String sql = "update AmountType\nset ";
            if (amountName != null) {
                sql += "AmountType = '" + amountName + "',\n";
            }
            if (amountToDram != null) {
                sql += "lastName = '" + amountToDram + "',\n";
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += "\nwhere AmountTypeID = " + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем District по id
     */

    public void deleteDistrictById(int districtId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Districts where DistrictID = " + districtId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем все District
     */

    public void deleteDistrictsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Districts";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * изменяем District по id
     */

    public void updateDistrict(int id, String name) {
        try (Connection con = this.getConnection()) {
            String sql = "update Districts\n" +
                    "set DistrictsName = '" + name + "'" +
                    "\nwhere DistrictId = " + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * удаляемProvince по id
     */

    public void deleteProvinceById(int provinceId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Provinces where ProvinceID = " + provinceId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * изменяем District-ы
     */

    public void deleteProvincesAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Provinces";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * изменяем Province по id
     */
    public void updateProvince(int id, String name) {
        try (Connection con = this.getConnection()) {
            String sql = "update Provinces\n" +
                    "set ProvinceName = '" + name + "'" +
                    "\nwhere ProvinceId = " + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * удаляем Region по iв
     */

    public void deleteRegionById(int regionId) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Regions where RegionID = " + regionId;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * удаляем Region-s
     */

    public void deleteRegionsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Regions";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * изменяем Region по id
     */

    public void updateRegion(int id, String name) {
        try (Connection con = this.getConnection()) {
            String sql = "update Regions\n" +
                    "set RegionName = '" + name + "'" +
                    "\nwhere RegionId = " + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * удаляем Project по id
     */
    public void deleteProjectById(int projectID) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Project where ProjectID = " + projectID;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * удаляем Projects
     */
    public void deleteProjectsAll() {
        try (Connection con = this.getConnection()) {
            String sql = "delete from Project";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * изменяем Project
     */

    public void updateProject(int id, String projectTitle, String projectDescription,
                              Date startDate, Date endDate, String duration, BigDecimal totalAmount,
                              int totalAmountTypeId, int subSectorId) {
        try (Connection con = this.getConnection()) {
            String sql = "update Project\nset ";
            if (projectTitle != null) {
                sql += "projectTitle = '" + projectTitle + "',\n";
            }
            if (projectDescription != null) {
                sql += "projectDescription = '" + projectDescription + "',\n";
            }
            if (startDate != null) {
                sql += "startDate = '" + startDate + "',\n";
            }
            if (endDate != null) {
                sql += "endDate = '" + endDate + "',\n";
            }
            if (duration != null) {
                sql += "duration = '" + duration + "',\n";
            }
            if (totalAmount != null) {
                sql += "totalAmount = '" + totalAmount + "',\n";
            }
            if (totalAmountTypeId != -1) {
                sql += "totalAmountTypeId = '" + totalAmountTypeId + "',\n";
            }
            if (subSectorId != -1) {
                sql += "subSectorId = '" + subSectorId + "',\n";
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += "\nwhere ProjectID = " + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteContactProject(int contactID, int projectID) {
        try (Connection con = this.getConnection()) {
            String sql = "delete from ContactsProjects where ContactID = ? and ProjectID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, contactID);
            ps.setInt(2, projectID);
            ps.executeUpdate();

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves
     */

    public void saveContact(Contacts contact) {
        try (Connection con = this.getConnection()) {
            String sql = "insert Contacts ( FirstName , lastName , Organisation , Email , Phone )\n" +
                    "values ( ? , ? , ? , ? , ? ) ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getOrganisation());
            ps.setString(4, contact.geteMail());
            ps.setString(5, contact.getPhone());
            int rs = ps.executeUpdate();
            ps.close();
            //   rs = null;
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int saveContactForProject(Contacts contact, int projectId) {
        int contId = -1;
        try (Connection con = this.getConnection()) {
            String sql = "insert Contacts ( FirstName , lastName , Organisation , Email , Phone )\n" +
                    "values ( ? , ? , ? , ? , ? )  ";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getLastName());
            ps.setString(3, contact.getOrganisation());
            ps.setString(4, contact.geteMail());
            ps.setString(5, contact.getPhone());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            sql = "insert ContactsProjects ( ContactID ,ProjectID )  values(?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, rs.getInt(1));
            ps.setInt(2, projectId);
            ps.executeUpdate();
            contId = rs.getInt(1);
            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contId;
    }

    public void saveLocation(int projectId, int districtId, BigDecimal percent) {
        try (Connection con = this.getConnection()) {
            String sql = "insert ProjectDistrict values(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, projectId);
            ps.setInt(2, districtId);
            ps.setBigDecimal(3, percent);
            ps.executeUpdate();

            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveDonor(int donorId, int projectId) {
        try (Connection con = this.getConnection()) {
            String sql = "insert SelectedDonors values(?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, donorId);
            ps.setInt(2, projectId);
            ps.executeUpdate();

            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int saveProject(Project project) {
        int projId = -1; //veradarcnumenq projecti id -n
        try (Connection con = this.getConnection()) {
            String sql = "insert Project values(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, project.getProjectTitle());
            ps.setString(2, project.getProjectDescription());
            ps.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
            ps.setString(5, project.getDuration());
            ps.setBigDecimal(6, project.getMoneyType().getTotalAmount());
            ps.setInt(7, project.getMoneyType().getId());
            ps.setInt(8, project.getSector().getSubSector().getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            projId = rs.getInt(1);
            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projId;
    }

    public void saveAll(Project project, List<Contacts> contactsList,
                        List<District> locationList, List<Donor> donorsList,
                        List<Contacts> delContact) throws SQLException {
        Connection con = this.getConnection();
        con.setAutoCommit(false);
        int projectId = saveProject(project);
        for (Contacts i : contactsList) {
            saveContactForProject(i, projectId);
        }
        for (Contacts i : delContact) {
            deleteContactProject(i.getId(),projectId);
        }
        for (District i : locationList) {
            saveLocation(projectId, i.getId(), i.getPercent());
        }

        for (Donor i : donorsList) {
            saveDonor(i.getId(), projectId);
        }
        con.commit();
    }

    private void closeConnection() {
        try {
            if (this.con != null) {
                this.con.close();
            }
            this.con = null;
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }
}