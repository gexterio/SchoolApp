package ua.com.foxminded.sql_jdbc_school.dto;

public class GroupDTO {
    final private Integer groupId;
    final private String groupName;

    public GroupDTO(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }
}
