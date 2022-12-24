package ua.com.foxminded.sqljdbcschool.dto;

import ua.com.foxminded.sqljdbcschool.util.DTOInputValidator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "groups")
public class GroupDTO {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;
    @Column(name = "group_name")
    private String groupName;

    public GroupDTO() {
    }

    public GroupDTO(GroupBuilder builder) {
        this.groupId = builder.groupId;
        this.groupName = builder.groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
            return group;
        }
    }
}
