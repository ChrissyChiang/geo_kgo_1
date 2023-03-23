export const flowMixin = {
  data() {
    return {
      jsplumbSetting: {
        // 動態錨點、位置自適應
        Anchors: [
          'Top',
          'TopCenter',
          'TopRight',
          'TopLeft',
          'Right',
          'RightMiddle',
          'Bottom',
          'BottomCenter',
          'BottomRight',
          'BottomLeft',
          'Left',
          'LeftMiddle'
        ],
        // 容器ID
        Container: 'efContainer',
        // 連線的樣式，直線或者曲線等，可選值:  StateMachine、Flowchart，Bezier、Straight
        Connector: ['Bezier', { curviness: 100 }],
        // Connector: ['Straight', {stub: 20, gap: 1}],
        Connector: [
          'Flowchart',
          {
            stub: 30,
            gap: 1,
            alwaysRespectStubs: false,
            midpoint: 0.5,
            cornerRadius: 10
          }
        ],
        //Connector: ['StateMachine', {margin: 5, curviness: 10, proximityLimit: 80}],
        // 鼠標不能拖動刪除線
        ConnectionsDetachable: false,
        // 刪除線的時候節點不刪除
        DeleteEndpointsOnDetach: false,
        /**
         * 連線的兩端端點類型：圓形
         * radius: 圓的半徑，越大圓越大
         */
        // Endpoint: ['Dot', {radius: 5, cssClass: 'ef-dot', hoverClass: 'ef-dot-hover'}],
        /**
         * 連線的兩端端點類型：矩形
         * height: 矩形的高
         * width: 矩形的寬
         */
        // Endpoint: ['Rectangle', {height: 20, width: 20, cssClass: 'ef-rectangle', hoverClass: 'ef-rectangle-hover'}],
        /**
         * 圖形端點
         */
        // Endpoint: ['Image', {src: '', cssClass: '', hoverClass: ''}],
        /**
         * 空白端點
         */
        Endpoint: ['Blank', { Overlays: '' }],
        // Endpoints: [['Dot', {radius: 5, cssClass: '', hoverClass: ''}], ['Rectangle', {height: 20, width: 20, cssClass: '', hoverClass: ''}]],
        /**
         * 連線的兩端端點樣式
         * fill: 顏色值，如：##1879ffa1，空字串則不顯示
         * outlineWidth: 外邊線寬度
         */
        EndpointStyle: { fill: '#1879ffa1', outlineWidth: 1 },
        // 是否打開jsPlumb的內部日誌記錄
        LogEnabled: true,
        /**
         * 連線的樣式
         */
        PaintStyle: {
          // 線的顏色
          stroke: '#312e2e',
          // 線的寬度
          strokeWidth: 4,
          // 設置外邊線的顏色，預設、透明
          //outlineStroke: 'transparent',
          // 線外邊的寬，值越大，線的點擊範圍越大
          outlineWidth: 50
        },
        DragOptions: { cursor: 'pointer', zIndex: 2000 },
        /**
         *  箭頭疊加
         */
        Overlays: [
          // 箭頭疊加
          [
            'Arrow',
            {
              // 箭頭尾部的寬度
              width: 25,
              // 從箭頭的尾部到頭部的距離
              length: 19,
              // 位置，0-1之間
              location: 1,
              // 方向，預設值為1(表示向前)，-1(表示向後)
              direction: 1,
              // 三角形角度，1為正三角形
              foldback: 1
            }
          ],
          [
            'Label',
            {
              location: 0.5,
              label: '',
              id: 'label',
              cssClass: 'aLabel'
            }
          ]
        ],
        // 繪製圖的模式 svg、canvas
        RenderMode: 'svg',
        // 滑鼠滑過線的樣式
        // HoverPaintStyle: { stroke: '#b0b2b5', strokeWidth: 3 },
        // 滑鼠滑過錨點效果
        // EndpointHoverStyle: {fill: 'red'}
        // 範圍，具有相同scope的點才可連接
        Scope: 'jsPlumb_DefaultScope'
      },
      /**
       * 連線參數
       */
      jsplumbConnectOptions: {
        isSource: true,
        isTarget: true,
        // 動態錨點、提供了4個方向 Continuous、AutoDefault
        anchor: 'Continuous',
        // 設置連線上面的label樣式
        labelStyle: {
          cssClass: 'flowLabel'
        },
        // 必須修改jsplumb原始碼，暫時不使用
        emptyLabelStyle: {
          cssClass: 'emptyFlowLabel'
        }
      },
      /**
       * 來源點配置參數
       */
      jsplumbSourceOptions: {
        // 設置可以拖拽的class name，只要滑鼠移動到該class name上的DOM，就可以拖曳連線
        filter: '.flow-node-drag',
        filterExclude: false,
        anchor: 'Continuous',
        // 是否允許自己連接自己
        allowLoopback: true,
        maxConnections: -1,
        onMaxConnections: function(info, e) {
          console.log(`超過了最大值連線: ${info.maxConnections}`)
        }
      },
      jsplumbTargetOptions: {
        // 設置可以拖拽的類名，只要鼠標移動到該類名上的DOM，就可以拖拽連線
        filter: '.flow-node-drag',
        filterExclude: false,
        anchor: 'Continuous',
        // 是否允許自己連接自己
        allowLoopback: true,
        dropOptions: { hoverClass: 'ef-drop-hover' }
      }
    }
  }
}
