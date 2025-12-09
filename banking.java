package com.bank;

import java.util.Scanner;
import java.sql.*;

public class banking {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1) Create Employee");
            System.out.println("2) Display Last 10 Employees");
            System.out.println("3) Raise Salary");
            System.out.println("4) Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
            case 1:
                System.out.print("Enter Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Age (20-60): ");
                int age = sc.nextInt();
                sc.nextLine();

                if (age < 20 || age > 60) {
                    System.out.println("Invalid Age! Returning to Main Menu...");
                    break;
                }

                System.out.print("Enter Designation (developer/manager/tester): ");
                String designation = sc.nextLine();

                double salary = 0;

                if (designation.equalsIgnoreCase("developer")) {
                    salary = 20000;
                } else if (designation.equalsIgnoreCase("manager")) {
                    salary = 25000;
                } else if (designation.equalsIgnoreCase("tester")) {
                    salary = 15000;
                } else {
                    System.out.println("Invalid designation! Returning to Main Menu...");
                    break;
                }

                System.out.println("Assigned Salary = " + salary);

                System.out.print("Is all information correct? (Y/N): ");
                String confirm = sc.nextLine();

                if (!confirm.equalsIgnoreCase("Y")) {
                    System.out.println("Returning to Main Menu...");
                    break;
                }
                try {
                    Connection con = DBConnection.getConnection();
                    String sql = "INSERT INTO employees (name, age, designation, salary) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setString(1, name);
                    ps.setInt(2, age);
                    ps.setString(3, designation);
                    ps.setDouble(4, salary);

                    ps.executeUpdate();

                    System.out.println("Employee Saved Successfully!");

                } catch (Exception e) {
                    System.out.println("Insert Error: " + e.getMessage());
                }

                break;

        
            case 2:
                try {
                    Connection con = DBConnection.getConnection();
                    String sql = "SELECT * FROM employees ORDER BY id DESC LIMIT 10";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();

                    System.out.println("\n--- LAST 10 EMPLOYEES ---");

                    while (rs.next()) {
                        System.out.println("ID: " + rs.getInt("id"));
                        System.out.println("Name: " + rs.getString("name"));
                        System.out.println("Age: " + rs.getInt("age"));
                        System.out.println("Designation: " + rs.getString("designation"));
                        System.out.println("Salary: " + rs.getDouble("salary"));
                        System.out.println("------------------------");
                    }

                } catch (Exception e) {
                    System.out.println("Display Error: " + e.getMessage());
                }
                break;

        
            case 3:
                System.out.print("Enter Employee Name: ");
                String empName = sc.nextLine();

                System.out.print("Enter Raise Percentage (1-10): ");
                int percent = sc.nextInt();
                sc.nextLine();

                if (percent < 1 || percent > 10) {
                    System.out.println("Invalid percentage!");
                    break;
                }

                try {
                    Connection con = DBConnection.getConnection();
                    String sql = "UPDATE employees SET salary = salary + (salary * ? / 100) WHERE name = ?";
                    PreparedStatement ps = con.prepareStatement(sql);

                    ps.setInt(1, percent);
                    ps.setString(2, empName);

                    int updated = ps.executeUpdate();

                    if (updated > 0) {
                        System.out.println("Salary Updated Successfully!");
                    } else {
                        System.out.println("Employee Not Found!");
                    }

                } catch (Exception e) {
                    System.out.println("Update Error: " + e.getMessage());
                }

                break;

  
            case 4:
                System.out.println("Thank you for using the application!");
                System.exit(0);

            default:
                System.out.println("Invalid Choice!");
            }
        }
    }
}



