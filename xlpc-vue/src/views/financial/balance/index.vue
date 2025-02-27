<template>
  <div>
    <el-card :bordered="false" shadow="never" class="ivu-mb-16" :body-style="{padding:0}">
      <div class="padding-add">
        <el-form
            ref="formValidate"
            :model="formValidate"
            :label-width="labelWidth"
            label-position="right"
            inline
            @submit.native.prevent
        >
          <el-form-item label="订单时间：">
            <el-date-picker
                clearable
                v-model="timeVal"
                type="daterange"
                :editable="false"
                @change="onchangeTime"
                format="yyyy/MM/dd"
                value-format="yyyy/MM/dd"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions"
                style="width: 250px"
                class="mr20"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="交易类型：">
            <el-select
                clearable
                v-model="formValidate.type"
                @change="selChange"
                class="form_content_width"
            >
              <el-option
                  :label="item"
                  :value="Object.keys(withdrawal)[index]"
                  v-for="(item, index) in Object.values(withdrawal)"
                  :key="index"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card :bordered="false" shadow="never">
      <el-table ref="table" :data="tabList" v-loading="loading" empty-text="暂无数据">
        <el-table-column label="ID" width="50">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="关联订单" min-width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.relation }}</span>
          </template>
        </el-table-column>
        <el-table-column label="交易时间" min-width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.add_time }}</span>
          </template>
        </el-table-column>
        <el-table-column label="交易金额" min-width="100">
          <template slot-scope="scope">
            <div v-if="scope.row.pm" class="z-price">+ {{ scope.row.number }}</div>
            <div v-else class="f-price">- {{ scope.row.number }}</div>
          </template>
        </el-table-column>
        <el-table-column label="用户" min-width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.nickname }}</span>
          </template>
        </el-table-column>
        <el-table-column label="交易类型" min-width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.type_name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" min-width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.mark }}</span>
          </template>
        </el-table-column>
      </el-table>
      <div class="acea-row row-right page">
        <pagination
          v-if="total"
          :total="total"
          :page.sync="formValidate.page"
          :limit.sync="formValidate.limit"
          @pagination="getList"
        />
      </div>
    </el-card>
  </div>
</template>
<script>
import { getBalanceList} from '@/api/financial';
import { formatDate } from '@/utils/validate';
import dateRadio from '@/components/dateRadio';
export default {
  name: 'balance',
  components: { dateRadio },
  filters: {
    formatDate(time) {
      if (time !== 0) {
        let date = new Date(time * 1000);
        return formatDate(date, 'yyyy-MM-dd hh:mm');
      }
    },
  },
  data() {
    return {
      pickerOptions: this.$timeOptions,
      total: 0,
      loading: false,
      tabList: [],
      withdrawal: [],
      selectIndexTime: '',
      formValidate: {
        type: '',
        time: '',
        keywords: '',
        page: 1,
        limit: 20,
      },
      timeVal: [],
      FromData: null
    };
  },
  computed: {
    labelWidth() {
      return '160px';
    },
    labelPosition() {
      return 'right';
    },
  },
  mounted() {
    this.getList();
  },
  methods: {
    onSelectDate(e) {
      this.formValidate.time = e;
      this.formValidate.page = 1;
      this.getList();
    },
    dateToMs(date) {
      let result = new Date(date).getTime();
      return result;
    },
    // 具体日期
    onchangeTime(e) {
      this.timeVal = e;
      this.formValidate.time = this.timeVal ? this.timeVal.join('-') : '';
      this.formValidate.page = 1;
      this.getList();
    },
    // 选择
    selChange(e) {
      this.formValidate.page = 1;
      this.formValidate.type = e;
      this.getList();
    },
    // 列表
    getList() {
      this.loading = true;
      getBalanceList(this.formValidate)
        .then((res) => {
          this.tabList = res.list;
          this.total = res.count;
          this.withdrawal = res.status;
          this.loading = false;
        })
        .catch((res) => {
          this.loading = false;
          this.$message.error(res.message);
        });
    }
  },
};
</script>
<style scoped lang="stylus">
.ivu-mt .type .item {
  margin: 3px 0;
}

.Refresh {
  font-size: 12px;
  color: #0256FF;
  cursor: pointer;
}

.ivu-form-item {
  margin-bottom: 10px;
}

.status ::v-deep .item~.item {
  margin-left: 6px;
}

.status ::v-deep .statusVal {
  margin-bottom: 7px;
}

/* .ivu-mt ::v-deep .ivu-table-header */
/* border-top:1px dashed #ddd!important */
.type {
  padding: 3px 0;
  box-sizing: border-box;
}

.tabBox_img {
  width: 36px;
  height: 36px;
  border-radius: 4px;
  cursor: pointer;

  img {
    width: 100%;
    height: 100%;
  }
}

.z-price {
  color: red;
}

.f-price {
  color: green;
}
</style>
