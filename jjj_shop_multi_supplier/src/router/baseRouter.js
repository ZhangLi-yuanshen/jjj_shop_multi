/*基础页面路由*/
let baserouter = [
    /*登录页面*/
    {
        path: '/login',
        name: 'login',
        meta: {
            title: '登录'
        },
        component: () =>
            import('@/views/login')
    },
    {
        path: '/',
        redirect: {
            name: 'Home'
        },
        meta: {
            title: '母版'
        },
        component: () =>
            import('@/views/layout/main'),
        children: [
            /*后台首页*/
            {
                path: '/home',
                name: 'Home',
                meta: {
                    title: '首页'
                },
                component: () =>
                    import('@/views/home/home')
            },
        ]
    },
];

/*错误页面路由*/
export const errpage = [{
  path: '*',
  name: 'Page404',
  meta: {
    title: '错误页面'
  },
  component: () =>
    import('@/views/error-page/404')
}]

export default baserouter;
