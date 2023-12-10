package dal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Notification;
import util.DbConnectionInt;
import util.singletonDbConnection;

public class NotificationDAO {
    private final DbConnectionInt dbConnection;

    public NotificationDAO() {
        dbConnection = singletonDbConnection.getInstance();
    }

    public void addNotification(Notification noti)
    {
        Connection con = dbConnection.getConnection();
        String add = "INSERT INTO Notification(eventID, notificationText) VALUES (?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(add);
            ps.setInt(1, noti.getEventID());
            ps.setString(2, noti.getNotificationText());
            ps.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List <Notification> getAllNotification()
    {
        Connection con = dbConnection.getConnection();
        String get = "SELECT * FROM Notification";
        List <Notification> result = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(get);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                result.add(new Notification(rs.getInt("eventID"), rs.getString("notificationText")));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}