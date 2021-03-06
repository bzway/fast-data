package ltd.fdsa.database.sql.columns.number.doubletype;

import java.util.function.BiFunction;

import ltd.fdsa.database.sql.testsupport.ColumnAliasTestSupport;
import ltd.fdsa.database.sql.testsupport.Consumers;
import org.junit.jupiter.api.Test;

import ltd.fdsa.database.sql.schema.Table;

class FloatColumnTest extends DoubleTypeColumnTest<FloatColumn, FloatColumnBuilder> implements ColumnAliasTestSupport<FloatColumn, FloatColumnBuilder, Double>
{
    @Override
    public FloatColumnBuilder getColumnBuilder(Table table, String name)
    {
        return new FloatColumnBuilder(table, name);
    }

    @Override
    public BiFunction<FloatColumn, String, FloatColumn> getAliasFunction()
    {
        return (column, alias) -> column.as(alias);
    }

    @Test
    void testDecimalColumnDefinition()
    {
        assertBuild(Consumers::noAction).isEqualTo("FLOAT DEFAULT NULL");
        assertBuild(builder -> builder.notNull()).isEqualTo("FLOAT NOT NULL");
        assertBuild(builder -> builder.defaultNull()).isEqualTo("FLOAT DEFAULT NULL");
        assertBuild(builder -> builder.noDefault()).isEqualTo("FLOAT");
        assertBuild(builder -> builder.size(3.3).defaultNull()).isEqualTo("FLOAT(3,3) DEFAULT NULL");
        assertBuild(builder -> builder.size(3.3).defaultValue(123.123)).isEqualTo("FLOAT(3,3) DEFAULT 123.123");
        assertBuild(builder -> builder.size(Double.valueOf(3.3)).defaultValue(Double.valueOf(123.123))).isEqualTo("FLOAT(3,3) DEFAULT 123.123");
        assertBuild(builder -> builder.size(3.3).notNull().defaultValue(123.123)).isEqualTo("FLOAT(3,3) NOT NULL DEFAULT 123.123");
    }
}
