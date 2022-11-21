package ua.com.foxminded.sqlJdbcSchool.dto;

import ua.com.foxminded.sqlJdbcSchool.util.DTOInputValidator;

import java.util.Objects;

public class GroupDTO {
    private static final DTOInputValidator validator = new DTOInputValidator();
    private final Integer groupId;
    private final String groupName;

    public GroupDTO(GroupBuilder builder) {
        this.groupId = builder.groupId;
        this.groupName = builder.groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public String toString() {
        return "groupId = " + groupId +
                "\"groupName = " + groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupDTO groupDTO = (GroupDTO) o;
        return groupId.equals(groupDTO.groupId) && groupName.equals(groupDTO.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName);
    }

    public static class GroupBuilder {
        private final String groupName;
        private Integer groupId;

        public GroupBuilder(String groupName) {
            this.groupName = groupName;
        }

        public GroupBuilder setId(int id) {
            this.groupId = id;
            return this;
        }

        public GroupDTO build() {
            GroupDTO group = new GroupDTO(this);
            validator.validateGroup(group);
            return group;
        }
    }
}
