package ltd.fdsa.database.sql.columns.string;

import java.util.function.BiFunction;

import ltd.fdsa.database.sql.testsupport.ColumnAliasTestSupport;
import ltd.fdsa.database.sql.testsupport.Consumers;
import org.junit.jupiter.api.Test;

import ltd.fdsa.database.sql.schema.Table;

class VarCharColumnTest extends StringColumnTest<VarCharColumn, VarCharColumnBuilder>
        implements ColumnAliasTestSupport<VarCharColumn, VarCharColumnBuilder, CharSequence>
{
    @Override
    public VarCharColumnBuilder getColumnBuilder(Table table, String name)
    {
        return new VarCharColumnBuilder(table, name);
    }

    @Override
    public BiFunction<VarCharColumn, String, VarCharColumn> getAliasFunction()
    {
        return (column, alias) -> column.as(alias);
    }

    @Test
    void testVarCharColumnDefinition()
    {
        assertBuild(Consumers::noAction).isEqualTo("VARCHAR DEFAULT NULL");
        assertBuild(builder -> builder.notNull()).isEqualTo("VARCHAR NOT NULL");
        assertBuild(builder -> builder.defaultNull()).isEqualTo("VARCHAR DEFAULT NULL");
        assertBuild(builder -> builder.noDefault()).isEqualTo("VARCHAR");
        assertBuild(builder -> builder.size(10).defaultNull()).isEqualTo("VARCHAR(10) DEFAULT NULL");
        assertBuild(builder -> builder.size(Integer.valueOf(10)).defaultValue("anyDefaultValue")).isEqualTo("VARCHAR(10) DEFAULT 'anyDefaultValue'");
        assertBuild(builder -> builder.size(10).notNull().defaultValue("anyDefaultValue")).isEqualTo("VARCHAR(10) NOT NULL DEFAULT 'anyDefaultValue'");
    }
}
