package selenium.aaold.cases.community.testcase;

import lombok.Builder;

@Builder
public class FriendshipTest {/*
    private final NotificationValidator notificationValidator;
    private final Supplier<SeleniumAccount> seleniumAccountSupplier;
    private final Navigate navigate;
    private final SelectCharacter selectCharacter;
    private final Login login;
    private final CommunityPage communityPage;
    private final OverviewPage overviewPage;


    public FriendshipTest testSendFriendRequest() {
        sendFriendRequestTo(account2.getCharacter1());

        verifyCannotSendFriendRequest(account2.getCharacter1());
        communityPage.closeAddFriendPage();
        verifySentFriendRequestInList(account2.getCharacter1());

        goToCommunityPageOf(account2, account2.getCharacter1(), 1);

        verifyFriendRequestNotifications(1);

        searchForPossibleFriends(account1.getCharacter1().getCharacterName());
        verifySearchResult(Arrays.asList(account1.getCharacter1()), Collections.emptyList());

        communityPage.closeAddFriendPage();
        verifyFriendRequestArrived(account1.getCharacter1());

        return this;
    }

    public FriendshipTest testCancelFriendRequest() {
        SeleniumAccount account1 = seleniumAccountSupplier.get();
        SeleniumAccount account2 = seleniumAccountSupplier.get();

        goToCommunityPageOf(account1, account1.getCharacter1());

        sendFriendRequestTo(account2.getCharacter1());

        communityPage.getSentFriendRequestsPageButton().click();

        SentFriendRequest friendRequest = communityPage.getSentFriendRequests().stream()
            .filter(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(account2.getCharacter1().getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("SentFriendRequest not found."));

        friendRequest.cancel();

        communityPage.getFriendsPageButton().click();
        searchForPossibleFriends(account2.getCharacter1().getCharacterName());
        verifySearchResult(Collections.emptyList(), Arrays.asList(account2.getCharacter1()));

        goToCommunityPageOf(account2, account2.getCharacter1(), 0);
        verifyFriendRequestNotifications(0);

        communityPage.getFriendsPageButton().click();
        searchForPossibleFriends(account1.getCharacter1().getCharacterName());
        verifySearchResult(Collections.emptyList(), Arrays.asList(account1.getCharacter1()));

        return this;
    }

    public FriendshipTest testDeclineFriendRequest() {
        SeleniumAccount account1 = seleniumAccountSupplier.get();
        SeleniumAccount account2 = seleniumAccountSupplier.get();

        goToCommunityPageOf(account1, account1.getCharacter1());

        sendFriendRequestTo(account2.getCharacter1());

        goToCommunityPageOf(account2, account2.getCharacter1(), 1);

        communityPage.getFriendRequestsPageButton().click();
        List<SeleniumFriendRequest> friendRequests = communityPage.getFriendRequests();
        friendRequests.stream()
            .filter(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(account1.getCharacter1().getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("FriendRequest not found."))
            .decline();

        assertFalse(
            communityPage.getFriendRequests().stream()
                .anyMatch(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(account1.getCharacter1().getCharacterName()))
        );

        communityPage.getFriendsPageButton().click();
        searchForPossibleFriends(account1.getCharacter1().getCharacterName());
        verifySearchResult(
            Collections.emptyList(),
            Arrays.asList(account1.getCharacter1())
        );

        goToCommunityPageOf(account1, account1.getCharacter1());

        searchForPossibleFriends(account2.getCharacter1().getCharacterName());
        verifySearchResult(
            Collections.emptyList(),
            Arrays.asList(account2.getCharacter1())
        );
        communityPage.closeAddFriendPage();

        communityPage.getSentFriendRequestsPageButton().click();
        assertFalse(
            communityPage.getSentFriendRequests().stream()
                .anyMatch(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(account2.getCharacter1().getCharacterName()))
        );

        return this;
    }

    public FriendshipTest testAcceptFriendRequest() {
        SeleniumAccount account1 = seleniumAccountSupplier.get();
        SeleniumAccount account2 = seleniumAccountSupplier.get();

        goToCommunityPageOf(account1, account1.getCharacter1());

        sendFriendRequestTo(account2.getCharacter1());

        goToCommunityPageOf(account2, account2.getCharacter1(), 1);

        verifyFriendRequestArrived(account1.getCharacter1());
        List<SeleniumFriendRequest> friendRequests = communityPage.getFriendRequests();
        friendRequests.stream()
            .filter(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(account1.getCharacter1().getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("FriendRequest not found."))
            .accept();

        assertFalse(
            communityPage.getFriendRequests().stream()
                .anyMatch(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(account1.getCharacter1().getCharacterName()))
        );

        communityPage.getFriendsPageButton().click();
        verifyFriendInList(account1.getCharacter1());

        goToCommunityPageOf(account1, account1.getCharacter1());

        verifyFriendInList(account2.getCharacter1());

        communityPage.getSentFriendRequestsPageButton().click();
        assertFalse(
            communityPage.getSentFriendRequests().stream()
                .anyMatch(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(account2.getCharacter1().getCharacterName()))
        );

        return this;
    }





    private void sendFriendRequestTo(SeleniumCharacter character) {
        searchForPossibleFriends(character.getCharacterName());

        communityPage.getCharactersCanBeFriendList().stream()
            .filter(p -> p.getCharacterName().equals(character.getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Friend not found in search result."))
            .addFriend(notificationValidator);
    }





    private void verifyCannotSendFriendRequest(SeleniumCharacter character) {
        searchForPossibleFriends(character.getCharacterName());
        verifySearchResult(
            Arrays.asList(character),
            Collections.emptyList()
        );
    }



    private void verifyFriendRequestNotifications(int numberOfWantedNotifications) {
        assertEquals(numberOfWantedNotifications, communityPage.getNumberOfFriendRequests());
    }

    private void verifySentFriendRequestInList(SeleniumCharacter character) {
        communityPage.getSentFriendRequestsPageButton().click();
        assertTrue(
            communityPage.getSentFriendRequests().stream()
                .anyMatch(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(character.getCharacterName()))
        );
    }

    private void verifyFriendRequestArrived(SeleniumCharacter character) {
        communityPage.getFriendRequestsPageButton().click();
        List<SeleniumFriendRequest> friendRequests = communityPage.getFriendRequests();
        assertTrue(
            friendRequests.stream()
                .anyMatch(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(character.getCharacterName()))
        );
    }

    private void verifyFriendInList(SeleniumCharacter character) {
        assertTrue(
            communityPage.getFriends().stream()
            .anyMatch(friend -> friend.getCharacterName().equals(character.getCharacterName()))
        );
    }*/
}
