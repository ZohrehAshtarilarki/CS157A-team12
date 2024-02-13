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
        String addQuery = "INSERT INTO comment (event_id,sjsu_id,comment_text) values (?,?,?);";
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
        String selectQuery = "SELECT * FROM comment WHERE event_id = ?;";
        List<Comment> commentList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, eventID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setCommentID(resultSet.getInt("comment_id"));
                comment.setEventID(resultSet.getInt("event_id"));
                comment.setSjsuID(resultSet.getInt("sjsu_id"));
                comment.setCommentText(resultSet.getString("comment_text"));

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