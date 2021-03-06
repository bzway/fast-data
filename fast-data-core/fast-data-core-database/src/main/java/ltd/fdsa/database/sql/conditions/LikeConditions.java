package ltd.fdsa.database.sql.conditions;

import static ltd.fdsa.database.sql.conditions.GenericCondition.GenericConditionType.IS_NOT_NULL;
import static ltd.fdsa.database.sql.conditions.GenericCondition.GenericConditionType.IS_NULL;
import static ltd.fdsa.database.sql.domain.LikeType.NONE;

import ltd.fdsa.database.sql.columns.Column;
import ltd.fdsa.database.sql.domain.LikeType;
import ltd.fdsa.database.sql.domain.Placeholder;

/**
 * @author zhumingwu
 *
 * @since 3/20/2021 10:36 AM
 */
public interface LikeConditions
{
    default Condition isLike(CharSequence value)
    {
        return value == null ? new GenericCondition(IS_NULL, this) : new IsLike(this, value, NONE);
    }

    default Condition isLike(CharSequence value, LikeType likeType)
    {
        return value == null ? new GenericCondition(IS_NULL, this) : new IsLike(this, value, likeType);
    }

    default Condition isLike(Column otherColumn)
    {
        return new IsLike(this, otherColumn, NONE);
    }

    default Condition isLike(Placeholder placeholder)
    {
        return new IsLike(this, placeholder, NONE);
    }

    default Condition isNotLike(CharSequence value)
    {
        return value == null ? new GenericCondition(IS_NOT_NULL, this) : new IsNotLike(this, value, NONE);
    }

    default Condition isNotLike(CharSequence value, LikeType likeType)
    {
        return value == null ? new GenericCondition(IS_NOT_NULL, this) : new IsNotLike(this, value, likeType);
    }

    default Condition isNotLike(Column otherColumn)
    {
        return new IsNotLike(this, otherColumn, NONE);
    }

    default Condition isNotLike(Placeholder placeholder)
    {
        return new IsNotLike(this, placeholder, NONE);
    }
}
