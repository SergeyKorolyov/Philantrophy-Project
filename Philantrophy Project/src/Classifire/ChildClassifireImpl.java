package Classifire;

/**
 * Created by Sergey on 27.07.2016.
 */

public abstract class ChildClassifireImpl extends ClassifierImpl implements ChildClassifire {
    private int parentId;

    public ChildClassifireImpl() {
    }

    public ChildClassifireImpl(int id, String name, int parentId) {
        super(id, name);
        this.parentId = parentId;
    }

    public ChildClassifireImpl(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Classifire.ChildClassifireImpl{" +
                "parentId=" + parentId +
                "} " + super.toString();
    }

    @Override
    public int getParentId() {
        return parentId;
    }
}
