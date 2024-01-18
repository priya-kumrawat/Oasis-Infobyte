package com.reservationsystem.main;
import java.sql.*;
import java.util.Scanner;
import java.util.Random;

public class ReservationSystem {
	
	  private static final int min = 1000;
	  private static final int max = 9999;

	  public static class user {
	    private String username;
	    private String password;

	    Scanner sc = new Scanner(System.in);

	    public user() {
	    }

	    public String getUsername() {
	      System.out.println("Enter Username : ");
	      username = sc.nextLine();
	      return username;
	    }

	    public String getPassword() {
	      System.out.println("Enter Password : ");
	      password = sc.nextLine();
	      return password;
	    }

	  }

	  public static class PnrRecord {
	    private int pnrNumber;
	    private String passangerName;
	    private String trainNumber;
	    private String classType;
	    private String journeyDate;
	    private String from;
	    private String to;

	    Scanner sc = new Scanner(System.in);

	    public int getpnrNumber() {
	      Random random = new Random();
	      pnrNumber = random.nextInt(max) + min;
	      return pnrNumber;
	    }

	    public String getpassangerName() {
	      System.out.print("Enter the Passanger Name : ");
	      passangerName = sc.nextLine();
	      return passangerName;
	    }

	    public String gettrainNumber() {
	      System.out.print("Enter the Train Number : ");
	      trainNumber = sc.nextLine();
	      return trainNumber;
	    }

	    public String getclassType() {
	      System.out.print("Enter the Class Type : ");
	      classType = sc.nextLine();
	      return classType;
	    }

	    public String getjourneyDate() {
	      System.out.print("Enter the Journey Date as 'YYYY'-'MM'-'DD' format : ");
	      journeyDate = sc.nextLine();
	      return journeyDate;
	    }

	    public String getfrom() {
	      System.out.print("Enter the starting place : ");
	      from = sc.nextLine();
	      return from;
	    }

	    public String getto() {
	      System.out.print("Enter the Destination place : ");
	      to = sc.nextLine();
	      return to;
	    }
	  }

	  public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    user u1 = new user();
	    String username = u1.getUsername();
	    String password = u1.getPassword();

	    String url = "jdbc:mysql://localhost:3306/reservationsystem";

	    try {
	      Class.forName("com.mysql.jc.jdbc.Driver");

	      try (Connection connection = DriverManager.getConnection(url, username, password)) {
	        System.out.println("User Connection Granted. \n");

	        while (true) {
	          String InsertQuery = "insert into reservetion values (?,?,?,?,?,?,?)";
	          String DeleteQuery = "DELETE FROM reservations WHERE pnr_number = ?";
	          String ShowQuery = "Select * from reservations";
	          System.out.println("Enter the choice : ");
	          System.out.println("1. Insert Record. ");
	          System.out.println("2. Delete Record. ");
	          System.out.println("3. Show All Records. ");
	          System.out.println("4. Exit. ");
	          int choice = sc.nextInt();

	          if (choice == 1) {
	            PnrRecord p1 = new PnrRecord();
	            int pnr_number = p1.getpnrNumber();
	            String passangerName = p1.getpassangerName();
	            String trainNumber = p1.gettrainNumber();
	            String classtype = p1.getclassType();
	            String journeyDate = p1.getjourneyDate();
	            String getfrom = p1.getfrom();
	            String getto = p1.getto();

	            try (PreparedStatement preparedStatement = connection.prepareStatement(InsertQuery)) {
	              preparedStatement.setInt(1, pnr_number);
	              preparedStatement.setString(2, passangerName);
	              preparedStatement.setString(3, trainNumber);
	              preparedStatement.setString(4, classtype);
	              preparedStatement.setString(5, journeyDate);
	              preparedStatement.setString(6, getfrom);
	              preparedStatement.setString(7, getto);

	              int rowsAffected = preparedStatement.executeUpdate();
	              if (rowsAffected > 0) {
	                System.out.println("Record added Successfully.");
	              } else {
	                System.out.println("No records were deleted.");
	              }
	            } catch (SQLException e) {
	              System.out.println("SQLException : " + e.getMessage());
	            }
	          }
	           else if (choice == 2) {
	            System.out.println("Enter the PNR number to delete the record :");
	            // int pnrNumber = sc.nextInt();
	            try (PreparedStatement preparedStatement = connection.prepareStatement(DeleteQuery)) {
	              int rowsAffected = preparedStatement.executeUpdate();

	              if (rowsAffected > 0) {
	                System.out.println("Record deleted successfully.");
	              } else {
	                System.out.println("No records were deleted.");
	              }
	            } catch (SQLException e) {
	              System.out.println("SQLException : " + e.getMessage());
	            }
	          } else if (choice == 3) {
	            try (PreparedStatement preparedStatement = connection.prepareStatement(ShowQuery);
	                ResultSet resultSet = preparedStatement.executeQuery()) {
	              System.out.println("All Record Printing.");

	              while (resultSet.next()) {
	                String pnrNumber = resultSet.getString("pnr_number");
	                String passengerName = resultSet.getString("passanger_name");
	                String trainNumber = resultSet.getString("train_numberr");
	                String classType = resultSet.getString("class_type");
	                String journeyDate = resultSet.getString("journey_date");
	                String fromLocation = resultSet.getString("from_location");
	                String toLocation = resultSet.getString("to_location");

	                System.out.println("PNR Number: " + pnrNumber);
	                System.out.println("Passenger Name: " + passengerName);
	                System.out.println("Train Number: " + trainNumber);
	                System.out.println("Class Type: " + classType);
	                System.out.println("Journey Date: " + journeyDate);
	                System.out.println("From Location: " + fromLocation);
	                System.out.println("To Location: " + toLocation);
	                System.out.println("----------------");

	              }
	            } catch (SQLException e) {
	              System.out.println("SQLException: " + e.getMessage());
	            }
	          }

	          else if (choice == 4) {
	            System.out.println("Exiting the Program. ");
	            break;
	          } else {
	            System.out.println("Invalid Choice Entered. ");
	          }
	        }
	      } catch (SQLException e) {
	        System.out.println("SQLException: " + e.getMessage());
	      }
	    } catch (ClassNotFoundException e) {
	      System.out.println("Error loading JDBC driver : " + e.getMessage());
	    }
	    sc.close();

	  }
	}

