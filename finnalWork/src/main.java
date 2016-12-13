
public class main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
         new LoginLayout();
        //loginLayout.createLoginLayout();
    }

}
/*
1:info 0:error 2:warning 3:question
1.在设计 中间表格布局的时候，一开始没想到拿什么去实现。本来想的是用JList 然后设置很多list，后来找到了JTabel。
2.同上， 在操作panel中，受到了思维定式的影响，就一直想要用边界布局，后来发现用流布局能更好的实现。
3.同上，设置表格的时候，发现一直设置不了表格的长宽 有限制，一开始我以为是center就那么大，后来改用流动布局了也还是这样、。
  最后找到问题是设置scrollpane居然 有个默认的长宽，得设置它的长宽才可以。
4.addrow 出现问题 原因是 创建JTable的时候 一开始找的教程有误，没有先转换为DefaultTableModel，再创建JTable

*/
