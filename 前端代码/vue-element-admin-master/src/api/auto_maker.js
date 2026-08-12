import requestHttp from "@/utils/request"
export default {
    // 分页查询
    async search(start, size, data) {
        return await requestHttp.post(`/rental/autoMaker/${start}/${size}`, data)
    },
    // 新增
    async save(data) {
        return await requestHttp.post('/rental/autoMaker', data)
    },
    // 修改
    async update(data) {
        return await requestHttp.put('/rental/autoMaker', data)
    },
    // 删除（支持单条和批量）
    async delete(ids) {
        return await requestHttp.delete(`/rental/autoMaker/${ids}`)
    }
}
