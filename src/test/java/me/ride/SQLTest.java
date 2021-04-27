package me.ride;

import me.ride.entity.User;
import me.ride.entity.car.Car;
import me.ride.entity.system.Order;

import java.sql.*;
import java.util.Date;

public class SQLTest {
    public static void main(String[] args) {
        String username = "postgres";
        String password = "17101993";
        String connectionUrl = "jdbc:postgresql://localhost/ride_me";
        Order order = new Order(100L, new User(), new Car(), new Date(), new Date());
        //Class.forName("com.mysql.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("select * from t_order where car_id=? AND (first_day between ? and ? or last_day between ? and ?);")) {
            System.out.println("Connected");
            preparedStatement.setInt(1, 2);
            preparedStatement.setDate(2, new java.sql.Date(order.getFirstDay().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(order.getLastDay().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(order.getFirstDay().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(order.getLastDay().getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                System.out.println(resultSet.getInt(1));
//                System.out.println(resultSet.getString(2));
                System.out.println("----------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
