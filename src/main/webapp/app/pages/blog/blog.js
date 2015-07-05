angular.module("app.blog", [])
    .config(['$stateProvider',
        function ($stateProvider) {
            $stateProvider
                .state('blog', {
                    url: '/blog',
                    templateUrl: '/app/pages/blog/blogView.html',
                    controller: "BlogController"
                });

        }])
    .controller("BlogController", ["$scope", "BlogService", "$mdDialog", "ToastService", "AuthService", "$anchorScroll", function ($scope, BlogService, $mdDialog, ToastService, AuthService, $anchorScroll) {
        $scope.posts = [];

        $scope.AuthService = AuthService;

        $scope.updatePosts = function () {
            BlogService.allPosts(function (success) {
                console.log(success.data);
                $scope.posts = success.data;
            }, function (error) {
                console.log(error);
            });
        }

        $scope.newPost = function ($event) {
            $mdDialog.show({
                templateUrl: "/app/components/dialogs/addBlogPostDialog/addBlogPostDialogView.html",
                controller: "AddBlogPostDialogController",
                targetEvent: $event
            })
                .then(function (success) {
                    ToastService.showDevwarsToast("fa-check-circle", "Successfully published", success.title);
                    $scope.updatePosts();
                }, function (error) {
                    //Otherwise means they just clicked cancel
                    if(error) {
                        ToastService.showDevwarsErrorToast("fa-exclamation-circle", "Error", "Could not publish post");
                    }
                })
        };

        $scope.editPost = function (post, $event) {
            $mdDialog.show({
                templateUrl: "/app/components/dialogs/addBlogPostDialog/addBlogPostDialogView.html",
                controller: "EditBlogPostDialogController",
                targetEvent: $event,

                locals: {
                    post: post
                }
            })
                .then(function (success) {
                    ToastService.showDevwarsToast("fa-check-circle", "Success", "Edited post");
                    $scope.updatePosts();
                }, angular.noop)
        }

        $scope.deletePost = function (post) {
            BlogService.deleteBlog(post.id, function (success) {
                ToastService.showDevwarsToast("fa-check-circle", "Successfully deleted post", success.data.title);
                $scope.updatePosts();
            }, function () {
                ToastService.showDevwarsErrorToast("fa-exclamation-circle", "Error", "Could not delete post");
            })
        }

        $scope.updatePosts();
    }])