<template>
  <div>
    <el-form
      ref="formValidate"
      :label-width="labelWidth"
      :label-position="labelPosition"
      class="tabform"
      @submit.native.prevent
      inline
    >
      <el-form-item label="订单搜索：" label-for="status1">
        <el-input
          v-model="formValidate.keywords"
          placeholder="请输入交易人"
          class="form_content_width"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchs">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tabList"
      ref="table"
      v-loading="loading"
      no-userFrom-text="暂无数据"
      no-filtered-userFrom-text="暂无筛选结果"
      class="table"
    >
      <el-table-column label="关联订单" min-width="130">
        <template slot-scope="scope">
          <span>{{ scope.row.order_id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="交易时间" min-width="130">
        <template slot-scope="scope">
          <span>{{ scope.row.update_time }}</span>
        </template>
      </el-table-column>
      <el-table-column label="交易金额" min-width="130">
        <template slot-scope="scope">
          <div v-if="scope.row.pm == 1" class="z-price">+{{ scope.row.number }}</div>
          <div v-if="scope.row.pm == 0" class="f-price">-{{ scope.row.number }}</div>
        </template>
      </el-table-column>
      <el-table-column label="交易用户" min-width="130">
        <template slot-scope="scope">
          <span>{{ scope.row.nickname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="交易类型" min-width="130">
        <template slot-scope="scope">
          <span>{{ scope.row.type_name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支付方式" min-width="130">
        <template slot-scope="scope">
          <span>{{ scope.row.pay_type_name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" min-width="130">
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
  </div>
</template>

<script>
import { getFlowList } from '@/api/statistic.js';

export default {
  name: 'commissionDetails',
  data() {
    return {
      modals: false,
      loading: false,
      formValidate: {
        ids: '',
        keywords: '',
        page: 1,
        limit: 15,
      },
      total: 0,
      tabList: []
    };
  },
  props: {
    ids: {
      type: Array,
      default: [],
    }
  },
  computed: {
    labelWidth() {
      return '180px';
    },
    labelPosition() {
      return 'right';
    },
  },
  mounted() {
    this.getList();
  },
  methods: {
    searchs() {
      this.formValidate.page = 1;
      this.getList();
    },
    // 列表
    getList() {
      this.formValidate.ids = this.ids.join(',');
      this.loading = true;
      getFlowList(this.formValidate)
        .then(async (res) => {
          this.tabList = res.list;
          this.total = res.count;
          this.loading = false;
        })
        .catch((res) => {
          this.loading = false;
          this.$message.error(res.message);
        });
    },
    reset() {
      this.formValidate.keywords = '';
      this.getList();
    },
    // 关闭按钮
    cancel() {
      this.$emit('close');
      this.formValidate = {
        ids: '',
        keywords: '',
        page: 1,
        limit: 15,
      };
    },
  },
};
</script>

<style lang="scss" scoped>
.colorred {
  color: #ff5722;
}
.colorgreen {
  color: #009688;
}
.search {
  width: 86px;
  height: 32px;
  background: #0256FF;
  border-radius: 4px;
  text-align: center;
  line-height: 32px;
  font-size: 13px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #ffffff;
  cursor: pointer;
}
.reset {
  width: 86px;
  height: 32px;
  border-radius: 4px;
  border: 1px solid rgba(151, 151, 151, 0.36);
  text-align: center;
  line-height: 32px;
  font-size: 13px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: rgba(0, 0, 0, 0.85);
  cursor: pointer;
}
.table {
  .ivu-table-default {
    overflow-y: auto;
    max-height: 350px;
  }
}
.dashboard-workplace {
  &-header {
    &-avatar {
      width: 64px;
      height: 64px;
      border-radius: 50%;
      margin-right: 16px;
      font-weight: 600;
    }

    &-tip {
      width: 82%;
      display: inline-block;
      vertical-align: middle;

      &-title {
        font-size: 13px;
        color: #000000;
        margin-bottom: 12px;
      }

      &-desc {
        &-sp {
          width: 33.33%;
          color: #17233d;
          font-size: 12px;
          display: inline-block;
        }
      }
    }

    &-extra {
      .ivu-col {
        p {
          text-align: right;
        }

        p:first-child {
          span:first-child {
            margin-right: 4px;
          }

          span:last-child {
            color: #808695;
          }
        }

        p:last-child {
          font-size: 22px;
        }
      }
    }
  }
}
.z-price {
  color: red;
}

.f-price {
  color: green;
}
</style>
