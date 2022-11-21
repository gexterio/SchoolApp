package ua.com.foxminded.sqlJdbcSchool.dto;

import java.util.Objects;

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
            validateGroupDTO(group);
            return group;
        }

        private void validateGroupDTO(GroupDTO group) {
            if (group.getGroupName() == null) {
                throw new IllegalArgumentException("groupName can't be NULL");
            }
            if (!group.getGroupName().matches("^\\d{2}+-+[A-Z]{2}")) {
                throw new IllegalArgumentException("invalidGroupName");
            }
        }
    }
}
