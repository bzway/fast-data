package ltd.fdsa.client.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import ltd.fdsa.client.mybatis.entity.Role;
import ltd.fdsa.client.mybatis.plus.service.RoleService;
import ltd.fdsa.database.controller.BaseController;
import ltd.fdsa.database.entity.Status;
import ltd.fdsa.database.sql.queries.Queries;
import ltd.fdsa.database.sql.schema.Table;
import ltd.fdsa.web.enums.HttpCode;
import ltd.fdsa.web.view.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role")
@Slf4j
public class RoleController extends BaseController {
    @Autowired
    private RoleService service;

    @GetMapping(value = "/list")
    public  Result<Object> list() {
        var table = Table.create("t_role").as("role");
        var role_id = table.intColumn("role_id").build();
        var role_name = table.varCharColumn("name").build();
        var role_status = table.intColumn("status").build();
        var select = Queries.select(table.getColumns()).from(table).where(role_status.eq(0));

        return Result.success( this.service.findWhere(select));
    }


    @GetMapping(value = "/insert")
    public Result<Object> insert(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) Integer sort,
            @RequestParam(required = false) Byte type,
            @RequestParam(required = false) Status status
    ) {
        try {
            var entity = new Role();
            entity.setName(name);
            entity.setRemark(remark);
            entity.setSort(sort);
            entity.setStatus(status);
            entity.setType(type);
//            user.setCreateTime(LocalDateTime.now());
//            user.setUpdateTime(LocalDateTime.now());
            return Result.success(this.service.insert(entity));
        } catch (Exception ex) {
            log.error("insert", ex);
            return Result.fail(HttpCode.BAD_REQUEST, ex.getLocalizedMessage());
        }
    }

    @GetMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable(value = "id") Integer id) {
        this.service.deleteById(id);
        return Result.success();
    }

    @GetMapping(value = "/update")
    public Result<Object> update(
            @RequestParam(required = true) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) Integer sort,
            @RequestParam(required = false) Byte type,
            @RequestParam(required = false) Status status
    ) {
        try {
            var entity = new Role();
            entity.setId(id);
            entity.setName(name);
            entity.setRemark(remark);
            entity.setSort(sort);
            entity.setStatus(status);
            entity.setType(type);
//            user.setCreateTime(LocalDateTime.now());
//            user.setUpdateTime(LocalDateTime.now());
            return Result.success(this.service.update(entity));
        } catch (Exception ex) {
            return Result.fail(HttpCode.BAD_REQUEST, ex);
        }
    }

}
