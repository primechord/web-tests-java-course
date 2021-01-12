package addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Groups extends ForwardingSet<GroupData> {
    private Set<GroupData> delegate;

    public Groups(Groups groupData) {
        this.delegate = new HashSet<>(groupData.delegate);
    }

    public Groups() {
        this.delegate = new HashSet<>();
    }

    public Groups(Collection<GroupData> result) {
        this.delegate = new HashSet<>(result);
    }

    @Override
    protected Set<GroupData> delegate() {
        return delegate;
    }

    public Groups withAdded(GroupData group) {
        Groups groups = new Groups(this);
        groups.add(group);
        return groups;
    }

    public Groups withOut(GroupData group) {
        Groups groups = new Groups(this);
        groups.remove(group);
        return groups;
    }

}
