import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";

interface DeleteConfirmationModelProps {
  Id: number | undefined;
  openModal: boolean;
  setOpenModal: (open: boolean) => void;
  handleDelete: (id: number | undefined) => void;
}

const DeleteConfirmationModel = ({
  Id,
  openModal,
  setOpenModal,
  handleDelete,
}: DeleteConfirmationModelProps) => {
  return (
    <>
      <Dialog
        open={openModal}
        onClose={() => {
          setOpenModal(false);
        }}
        aria-labelledby="Delete"
        aria-describedby="Delete an item"
      >
        <DialogTitle id="dialog-title">{"Delete Confirmation"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="dialog-description">
            Are you sure you want to delete Item with id:{Id}?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => {
              setOpenModal(false);
            }}
          >
            Cancel
          </Button>
          <Button color="primary" onClick={() => handleDelete(Id)} autoFocus>
            Delete
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default DeleteConfirmationModel;
