import requestHttp from "@/utils/request";

export default{
    // 查询部门列表（树形）
    async search(params){
        return await requestHttp.post("/rental/dept/list",params)
    },
    // 查询部门树（用于选择上级部门）
    async selectTree(){
        return await requestHttp.get("/rental/dept")
    },
    // 新增部门
    async save(params){
        return await requestHttp.post("/rental/dept/save",params)
    },
    // 修改部门
    async update(params){
        return await requestHttp.put("/rental/dept",params)
    },
    // 删除部门
    async delete(id){
        return await requestHttp.delete(`/rental/dept/${id}`)
    },
    // 查询部门下是否有子部门
    async hasChildren(id){
        return await requestHttp.get(`/rental/dept/${id}`)
    }
}
