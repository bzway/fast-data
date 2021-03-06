package ltd.fdsa.database.sql.domain;

import ltd.fdsa.database.sql.utils.Indentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author zhumingwu
 * @since 3/20/2021 10:36 AM
 */
@AllArgsConstructor
@ToString
public class PlainQueryable implements Queryable {
    private String value;

    @Getter
    private String alias;

    @Override
    public String getValue(BuildingContext context, Indentation indentation) {
        return value;
    }

    public Queryable as(@SuppressWarnings("hiding") String alias) {
        return new PlainQueryable(value, alias);
    }
}
