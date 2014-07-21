package org.javers.core.diff;

import org.javers.common.collections.Optional;
import org.javers.core.commit.CommitMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author bartosz walacik
 */
public class DiffBuilder {
    private final List<Change> changes = new ArrayList<>();

    private DiffBuilder() {
    }

    public static DiffBuilder diff(){
        return new DiffBuilder();
    }

    public static Diff empty(){
        return new Diff(Collections.EMPTY_LIST);
    }

    public DiffBuilder addChange(Change change, Optional<Object> affectedCdo) {
        addChange(change);
        change.setAffectedCdo(affectedCdo);
        return this;
    }

    public DiffBuilder addChange(Change change) {
        changes.add(change);
        return this;
    }

    public DiffBuilder addChanges(Collection<Change> changeSet, Optional<CommitMetadata> commitMetadata) {
        if (commitMetadata.isPresent()) {
            for (Change change : changeSet) {
                addChange(change);
                change.bindToCommit(commitMetadata.get());
            }
        } else {
            for (Change change : changeSet) {
                addChange(change);
            }
        }

        return this;
    }

    public Diff build() {
        return new Diff(changes);
    }
}
