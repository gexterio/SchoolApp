package ua.com.foxminded.sql_jdbc_school.dto;

public class GroupDTO {
    private final Integer groupId;
    private final String groupName;

    public GroupDTO(GroupBuilder builder) {
        this.groupId = builder.groupId;
        this.groupName = builder.groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public String toString() {
        return "groupId = " + groupId +
                "\"groupName = " + groupName;
    }

    public static class GroupBuilder {
        private final String groupName;
        private Integer groupId;

        public GroupBuilder(String groupName) {
            this.groupName = groupName;
        }
        public GroupBuilder setId (int id) {
            this.groupId = id;
            return this;
        }
        public GroupDTO build () {
            GroupDTO group = new GroupDTO(this);
            validateGroupDTO(group);
            return group;
        }
        private void validateGroupDTO (GroupDTO group) {
            if (groupName == null) {
                throw new IllegalArgumentException("groupName can't be NULL");
            }
            if (!group.groupName.matches("^\\d{2}+-+[A-Z]{2}")) {
                throw new IllegalArgumentException("invalidGroupName");
            }
        }
    }
}
