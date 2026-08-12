<template>
  <div>
    <el-main>
      <!-- 查询表单 -->
      <el-form :inline="true" :model="makerModel" size="small" label-width="100px">
        <el-form-item label="厂商名称">
          <el-input v-model="makerModel.name" placeholder="厂商名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="onSubmit">查询</el-button>
          <el-button type="warning" icon="el-icon-refresh" @click="resetForm">重置</el-button>
          <el-button type="success" icon="el-icon-plus" @click="handleCreate">新增</el-button>
          <el-button type="danger" icon="el-icon-delete" @click="deleteBatch">删除选中</el-button>
        </el-form-item>
      </el-form>
      <!-- 查询表单结束 -->

      <!-- 数据表格 -->
      <el-table :data="tableData" style="width: 100%;margin-bottom: 20px;" border stripe
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="序号" width="80">
          <template slot-scope="scope">{{ (start - 1) * size + scope.$index + 1 }}</template>
        </el-table-column>
        <el-table-column prop="name" label="厂商名称"></el-table-column>
        <el-table-column prop="orderLetter" label="排序字母"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit"
              @click="handleUpdate(scope.row)">修改</el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete"
              @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 数据表格结束 -->

      <!-- 分页组件 -->
      <el-pagination background layout="total,prev, pager, next,jumper"
        :current-page="start" :page-size="size" :total="total"
        @current-change="search">
      </el-pagination>
      <!-- 分页组件结束 -->

      <!-- 对话框 -->
      <el-dialog :title="titleMap[dialogStatus]" :visible.sync="dialogFormVisible">
        <el-form :model="saveMaker" ref="form" :rules="rules" label-width="100px">
          <el-form-item label="厂商名称" prop="name">
            <el-input v-model="saveMaker.name"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <!-- 因为新增和修改用的是同一个对话框，所以要区分 -->
          <el-button type="primary" @click="dialogStatus === 'create' ? createData() : updateData()">确 定</el-button>
        </div>
      </el-dialog>
      <!-- 对话框结束 -->
    </el-main>
  </div>
</template>

<script>
// 导入 auto_maker.js
import makerApi from '@/api/auto_maker.js'
export default {
  name: 'makerList',
  // 数据
  data() {
    return {
      makerModel: {},           // 查询条件对象
      start: 1,                 // 当前页码
      size: 5,                  // 每页条数
      total: 0,                 // 总记录数
      tableData: [],            // 表格数据
      multipleSelection: [],    // 记录选中项的 id
      dialogFormVisible: false, // 对话框显示/隐藏
      saveMaker: {},            // 对话框绑定的对象
      dialogStatus: '',         // 当前是 create 还是 update
      titleMap: {
        create: '新增厂商',
        update: '修改厂商'
      },
      rules: {
        name: [
          { required: true, message: '请输入厂商名称', trigger: 'blur' }
        ]
      }
    }
  },
  // 初始化自动调用
  created() {
    this.search()
  },
  // 方法
  methods: {
    // 查询数据
    async search(start = 1) {
      this.start = start
      const res = await makerApi.search(this.start, this.size, this.makerModel)
      if (res.success) {
        this.tableData = res.data.records
        this.total = res.data.total
      }
    },
    // 点击查询按钮
    onSubmit() {
      this.search()
    },
    // 重置表单
    resetForm() {
      this.makerModel = {}
      this.search()
    },
    // 点击新增按钮
    handleCreate() {
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.saveMaker = {}
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    // 批量删除
    deleteBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message({ type: 'warning', message: '请选择要删除的数据!' })
        return
      }
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const res = await makerApi.delete(this.multipleSelection.join(','))
        if (res.success) {
          this.$message({ type: 'success', message: '删除成功!' })
          this.search()
        }
      }).catch(() => {})
    },
    // 点击修改按钮 — 深拷贝行数据，避免直接修改表格行
    handleUpdate(row) {
      this.saveMaker = { ...row }
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    // 单条删除
    handleDelete(row) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        const res = await makerApi.delete(row.id)
        if (res.success) {
          this.$message({ type: 'success', message: '删除成功!' })
          this.search()
        }
      }).catch(() => {})
    },
    // 表格选中变化
    handleSelectionChange(val) {
      this.multipleSelection = val.map(item => item.id)
    },
    // 新增提交
    createData() {
      this.$refs['form'].validate(async valid => {
        if (!valid) return
        const res = await makerApi.save(this.saveMaker)
        if (res.success) {
          this.$message({ type: 'success', message: '新增成功!' })
          this.dialogFormVisible = false
          this.search()
        }
      })
    },
    // 修改提交
    updateData() {
      this.$refs['form'].validate(async valid => {
        if (!valid) return
        const res = await makerApi.update(this.saveMaker)
        if (res.success) {
          this.$message({ type: 'success', message: '修改成功!' })
          this.dialogFormVisible = false
          this.search()
        }
      })
    }
  }
}
</script>

<style></style>
