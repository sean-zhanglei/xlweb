<template>
  <el-card :bordered="false" shadow="never" class="ivu-mt-16" v-loading="spinShow">
    <div class="acea-row row-between-wrapper mb20">
      <div class="statics-header-title">
        <h4>交易概况</h4>
        <el-tooltip placement="right-start">
          <i class="el-icon-question ml10"></i>
          <div slot="content">
            <div>营业额</div>
            <div>商品支付金额、充值金额</div>
            <br />
            <div>商品支付金额</div>
            <div>
              选定条件下，用户购买商品的实际支付金额，包括微信支付、余额支付
            </div>
            <br />
            <div>充值金额</div>
            <div>选定条件下，用户成功充值的金额</div>
            <br />
            <div>支出金额</div>
            <div>余额支付金额、商品退款金额</div>
            <br />
            <div>余额支付金额</div>
            <div>用户下单时使用余额实际支付的金额</div>
            <br />
            <div>商品退款金额</div>
            <div>用户成功退款的商品金额</div>
          </div>
        </el-tooltip>
      </div>
      <div class="acea-row">
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
        <el-button type="primary" @click="onSeach">查询</el-button>
      </div>
    </div>
    <div class="acea-row mb20">
      <div class="infoBox acea-row mb30" v-for="(item, index) in list" :key="index">
        <div
          class="iconCrl mr15"
          :class="{
            one: index % 4 == 0,
            two: index % 4 == 1,
            three: index % 4 == 2,
            four: index % 4 == 3,
          }"
        >
          <i class="iconfont" :class="item.icon"></i>
        </div>
        <div class="info">
          <span class="sp1" v-text="item.name"></span>
          <span
            class="sp2"
            v-if="index === list.length - 1"
            v-text="item.money ? (parseInt(item.money * 100) / 100).toFixed(2) : '0.00'"
          ></span>
          <span class="sp2" v-else v-text="item.money ? item.money : '0.00'"></span>
          <span class="content-time spBlock"
            >环比增长：<i class="content-is" :class="Number(item.rate) >= 0 ? 'up' : 'down'">{{ item.rate }}%</i
            ><i
              :style="{ color: Number(item.rate) >= 0 ? '#F5222D' : '#39C15B' }"
              :class="[Number(item.rate) >= 0 ? 'el-icon-caret-top' : 'el-icon-caret-bottom']"
          /></span>
        </div>
      </div>
    </div>
    <echarts-new :option-data="optionData" :styles="style" height="100%" width="100%" v-if="optionData"></echarts-new>
  </el-card>
</template>

<script>
import { statisticBottomTradeApi} from '@/api/statistic';
import echartsNew from '@/components/echartsNew/index';
import { formatDate } from '@/utils/validate';
export default {
  name: 'transaction',
  components: {
    echartsNew,
  },
  data() {
    return {
      grid: {
        xl: 8,
        lg: 8,
        md: 8,
        sm: 24,
        xs: 24,
      },
      pickerOptions: this.$timeOptions,
      name: '近30天',
      timeVal: [],
      dataTime: '',
      list: {},
      optionData: {},
      style: { height: '400px' },
      spinShow: false,
    };
  },
  created() {
    const end = new Date();
    const start = new Date();
    start.setTime(start.setTime(new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate() - 29)));
    this.timeVal = [start, end];
    this.dataTime = formatDate(start, 'yyyy/MM/dd') + '-' + formatDate(end, 'yyyy/MM/dd');
  },
  mounted() {
    this.getStatistics();
  },
  methods: {
    onSeach() {
      this.getStatistics();
    },
    // 具体日期
    onchangeTime(e) {
      this.timeVal = e;
      this.dataTime = this.timeVal ? this.timeVal.join('-') : '';
      this.name = this.dataTime;
    },
    // 统计
    getStatistics() {
      this.spinShow = true;
      statisticBottomTradeApi({ data: this.dataTime })
        .then(async (res) => {
          const cardLists = res;
          const incons = [
            'iconyingyee', // 营业额
            // 'iconjiaoyijine', // 交易金额
            'iconshangpinzhifujine', // 商品支付金额
            // 'icongoumaihuiyuanjine', // 购买会员金额
            'iconchongzhijianshu', // 充值金额
            // 'iconxianxiashouyinjine', // 线下收银金额
            'iconzhichujine', // 支出金额
            'iconyuezhifujine', //  余额支付金额
            // 'iconzhifuyongjinjine', // 支付佣金金额
            'iconshangpintuikuanjine', // 商品退款金额
          ];
          for (var i = 0; i < cardLists.series.length; i++) {
            this.$set(cardLists.series[i], 'icon', incons[i]);
          }
          this.list = cardLists.series;
          this.get(cardLists);
          this.spinShow = false;
        })
        .catch((res) => {
          this.$message.error(res.message);
          this.spinShow = false;
        });
    },
    get(extract) {
      let dataList = extract.series.filter((item) => {
        return item.type === 1;
      });
      let legend = dataList.map((item) => {
        return item.name;
      });
      let col = ['#5B8FF9', '#5AD8A6', '#5D7092', '#F5222D', '#FFAB2B', '#B37FEB'];
      let seriesData = [];
      dataList.map((item, index) => {
        let series = [];
        Object.keys(item.value).forEach((key) => {
          series.push(Number(item.value[key]));
        });
        seriesData.push({
          name: item.name,
          type: 'line',
          data: series,
          itemStyle: {
            normal: {
              color: col[index],
            },
          },
          smooth: true,
        });
      });
      this.optionData = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985',
            },
          },
        },
        legend: {
          x: 'center',
          data: legend,
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        toolbox: {
          feature: {
            saveAsImage: {},
          },
        },
        xAxis: {
          type: 'category',
          boundaryGap: true,
          axisLabel: {
            interval: 0,
            rotate: 40,
            textStyle: {
              color: '#000000',
            },
          },
          data: extract.x,
        },
        yAxis: {
          type: 'value',
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false,
          },
          axisLabel: {
            textStyle: {
              color: '#7F8B9C',
            },
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: '#F5F7F9',
            },
          },
        },
        series: seriesData,
      };
    }
  }
};
</script>

<style scoped lang="scss">
.one {
  background: #0256FF;
}
.two {
  background: #00c050;
}
.three {
  background: #ffab2b;
}
.four {
  background: #b37feb;
}
.up,
.el-icon-caret-top {
  color: #f5222d;
  font-size: 12px;
  opacity: 1 !important;
}

.down,
.el-icon-caret-bottom {
  color: #39c15b;
  font-size: 12px;
}
.curP {
  cursor: pointer;
}
.header {
  &-title {
    font-size: 16px;
    color: rgba(0, 0, 0, 0.85);
  }
  &-time {
    font-size: 12px;
    color: #000000;
    opacity: 0.45;
  }
}

.iconfont {
  font-size: 16px;
  color: #fff;
}

.iconCrl {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  text-align: center;
  line-height: 32px;
  opacity: 0.7;
}

.lan {
  background: #0256FF;
}

.iconshangpinliulanliang {
  color: #fff;
}

.infoBox {
  width: 20%;
  margin: 30px;
  @media screen and (max-width: 1300px) {
    width: 25%;
  }
  @media screen and (max-width: 1200px) {
    width: 33%;
  }
  @media screen and (max-width: 900px) {
    width: 50%;
  }
}

.info {
  .sp1 {
    color: #666;
    font-size: 14px;
    display: block;
  }
  .sp2 {
    font-weight: 400;
    font-size: 30px;
    color: rgba(0, 0, 0, 0.85);
    display: block;
  }
  .sp3 {
    font-size: 12px;
    font-weight: 400;
    color: rgba(0, 0, 0, 0.45);
    display: block;
  }
}
</style>
