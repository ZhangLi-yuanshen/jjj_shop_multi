/*封装菜单的方法*/
export const isMenu = (list) => {
  for(let i=0;i<list.length;i++){
    let item=list[i];
    console.log(item,item.name);
    if(item.isRoute==0||((item.isRoute==1||item.isRoute==2)&&!item.isMenu)){
      list.splice(i,1);
      i--;
    }else{
      if(Object.prototype.toString.call(item.children) === '[object Array]'){
         isMenu(item.children);
      }
    }
  }
}

function allChildMenu(item,arr){
  let list=[];
  if(typeof item.children !='undefined'){
    for(let i=0,leng=item.children.length;i<leng;i++){
      let child=item.children[i];
      if((child.isRoute==1||child.isRoute==2)&&child.isMenu){
        let obj={
          name:child.name,
          icon:child.icon,
          path:child.path,
          alias:child.alias,
          redirectName:child.redirectName,
        }
        list.push(obj);
      }
    }
  }
  arr=arr.concat(list);
  return list;
}
