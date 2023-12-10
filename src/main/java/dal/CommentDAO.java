package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Comment;
import model.Event;
import util.DbConnectionInt;
import util.singletonDbConnection;

public class CommentDAO {
    private final DbConnectionInt dbConnection;

    public CommentDAO() {
        dbConnection = singletonDbConnection.getInstance();
    }

    public void addComment(int eventID,int sjsuID, String text)
    {
        Connection connection = dbConnection.getConnection();
        String addQuery = "INSERT INTO Comment (eventID,sjsuID,commentText) values (?,?,?);";
        try {
            PreparedStatement ps = connection.prepareStatement(addQuery);
            ps.setInt(1, eventID);
            ps.setInt(2, sjsuID);
            ps.setString(3, text);
            ps.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List <Comment> getAllCommentbyEvent (int eventID)
    {
        Connection connection = dbConnection.getConnection();
        String selectQuery = "SELECT * FROM Comment WHERE eventID = ?;";
        List<Comment> commentList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, eventID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setCommentID(resultSet.getInt("commentID"));
                comment.setEventID(resultSet.getInt("eventID"));
                comment.setSjsuID(resultSet.getInt("sjsuID"));
                comment.setCommentText(resultSet.getString("commentText"));

                commentList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately later
        } finally {
            dbConnection.closeConnection();
        }

        return commentList;
    }

}