package Main;

import Doa.Connect;
import Project.*;
import Doa.Connect.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        /**
         * Определение объектов
         */
        District d1 = new District(3, "d1", 1, new BigDecimal(20));
        District d2 = new District(2, "d2", 2, new BigDecimal(20));
        if (d1.equals(d2)) {
            System.out.println("Districts are equal\n");
        }
        ArrayList<District> districtArray = new ArrayList<>();
        districtArray.add(d1);
        districtArray.add(d2);
        Province p1 = new Province(1, "p1", 1, districtArray);
        Province p2 = new Province(2, "p2", 2, districtArray);
        if (p1.equals(p2)) {
            System.out.println("Provinces are equal\n");
        }
        ArrayList<Province> provinceArray = new ArrayList<>();
        provinceArray.add(p1);
        provinceArray.add(p2);
        Region r1 = new Region(1, "r1", provinceArray);
        Region r2 = new Region(2, "r2", provinceArray);
        if (r1.equals(r2)) {
            System.out.println("Regions are equal\n");
        }
        if (r1.equals(r1)) {
            System.out.println("Regions are equal\n");
        }
        BigDecimal value1 = new BigDecimal(1);
        BigDecimal value2 = new BigDecimal(510);
        Curency currency1 = new Curency(1, "AMD", value1, new BigDecimal(20));
        Curency currency2 = new Curency(2, "EVRO", value2, new BigDecimal(25));
        if (currency1.equals(currency2)) {
            System.out.println("Currency types are equal\n");
        }
        ArrayList<Region> loc1 = new ArrayList<Region>(10);
        loc1.add(r1);
        loc1.add(r1);

        Curency cr1 = new Curency(1, "cr1" ,new BigDecimal(30),new BigDecimal(30));
        SubSector ss1 = new SubSector(1,"Name1",1);
        ArrayList<SubSector> sA1 = new ArrayList<SubSector>();
        sA1.add(ss1);
        Sector sec1 = new Sector(1,"Sector1" ,ss1);

        Organisation org1 = new Organisation(1, "org1","Caxkadzor", "private");

        Contacts cn1 = new Contacts(1, "cn1","cn11", "org1", "e_mail", "phone1");
        ArrayList<Contacts> cArr1 = new ArrayList<Contacts>();
        cArr1.add(cn1);
        ArrayList<Contacts> cArr2 = new ArrayList<Contacts>();

        Donor don1 = new Donor(1 , "Don1");
        Donor don2 = new Donor(2 , "Don2");
        ArrayList<Donor> donArr = new ArrayList<Donor>();
        donArr.add(don1);
        donArr.add(don2);

        Project pro12 = new Project (1,  "p1", "description1", new Date(999), new Date(99999), cr1, sec1,
                cArr1, loc1 , donArr);

        /**
         * Загрузка данных из Базы
         */

        Connect myDbTest = new Connect();

//        List<Region> regions = myDbTest.loadRegions(1);
//        for (int i = 0; i < regions.size(); ++i) {
//            System.out.println(regions.get(i).toString());
//        }
//        List<District> districts = myDbTest.loadDistricts(1);
//        for (int i = 0; i < districts.size(); ++i) {
//            System.out.println(districts.get(i).toString());
//        }
//        List<Province> provinces = myDbTest.loadProvinces(1);
//        for (int i = 0; i < provinces.size(); ++i) {
//            System.out.println(provinces.get(i).toString());
//        }
//        List<Sector> sectors = myDbTest.loadSectors();
//        for (int i = 0; i < sectors.size(); ++i) {
//            System.out.println(sectors.get(i).toString());
//        }
//        List<Project> projects = myDbTest.loadProjects();
//        for (int i = 0; i < projects.size(); ++i) {
//            System.out.println(projects.get(i).toString());
//        }

        /**
         * Изменение ,удаление и добавление данных в Базу
         */
//        myDbTest.deleteSubSectorById(1003);
//        myDbTest.deleteDistrictById(4);
//        myDbTest.updateProvince(2, "LasVegas");
//        BigDecimal a = new BigDecimal(5.5);
//        myDbTest.updateProject(2, "Title", "op", null, null, null, a, 2, 2);

//        myDbTest.saveContact(cn1);
//        myDbTest.saveProject(pro12);
        myDbTest.saveAll(pro12, cArr1, districtArray, donArr ,cArr2);
    }
}