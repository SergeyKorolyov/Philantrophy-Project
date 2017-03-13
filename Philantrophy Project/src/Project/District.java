package Project;

import Classifire.ChildClassifireImpl;
import Classifire.ClassifierImpl;

import java.math.BigDecimal;

/**
 * Created by Sergey on 27.07.2016.
 */
public class District extends ChildClassifireImpl {
    private BigDecimal percent ;
    public District() {
    }

    public District(int id, String name, int parentId , BigDecimal percent) {
        super(id, name, parentId);
        if (percent.compareTo(new BigDecimal(100)) > 0) {
            throw new IllegalArgumentException("percent cannot be greater than 100.");
        } else {
            this.percent = percent;
        }
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }
}
