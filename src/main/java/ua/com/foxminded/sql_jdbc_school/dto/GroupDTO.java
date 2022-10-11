package ua.com.foxminded.sql_jdbc_school.dto;

public class GroupDTO {
    private final Integer groupId;
    private final String groupName;

    public GroupDTO(Integer groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }
}
